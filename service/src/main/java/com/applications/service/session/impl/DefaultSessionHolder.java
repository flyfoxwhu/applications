package com.applications.service.session.impl;

import com.alibaba.fastjson.JSON;
import com.applications.service.redis.intf.RedisManager;
import com.applications.service.session.intf.Session;
import com.applications.service.session.intf.SessionHolder;
import com.applications.common.utils.StringUtil;
import com.applications.common.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service("sessionHolder")
public class DefaultSessionHolder implements SessionHolder {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSessionHolder.class);

    private static final int DEFAULT_EXPIRE_TIME = 60 * 60 * 24 * 7;    //默认超时时间,7天

    @Autowired
    private final RedisManager redisManager = null;

    private final int expireTime;    //超时时间

    private final String B2B_SESSION_PREFIX = "B2B_APP_SESSION_";    //B2B SESSION的前缀
    private final String B2B_USER_SESSION = "B2B_APP_USER_SESSION_";    //B2B SESSION的前缀


    public DefaultSessionHolder() {
        this(DEFAULT_EXPIRE_TIME);
    }

    public DefaultSessionHolder(int expire) {
        this.expireTime = expire;
    }

    protected int getExpireTime() {
        return expireTime;
    }

    @Override
    public Session getSession(String id) {
        try {
            Session session = getSessionFromRedis(id);
            return session;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Session createSession(String id, HashMap<String, Object> attrs) {
        try {
            if (StringUtil.isEmpty(id)) {
                id = UUIDUtils.generateUUID();
            }
            Session session = new DefaultSession(id);
            if (attrs != null) {
                session.setAttrs(attrs);
            }
            if (put(session)) {
                return session;
            }
            return null;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean replaceSession(String sessionId, String userId) {
        try {
            //1、得到老的session并移除
            String userSessionKey = getUserSessionKey(userId);
            String oldSessionId = redisManager.getString(userSessionKey);
            if (StringUtil.isNotBlank(oldSessionId)) {
                redisManager.delete(getSessionKey(oldSessionId));
            }
            //2、替换为新的session
            redisManager.putJson(userSessionKey, sessionId);
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean put(Session session) {
        try {
            String json = serializeSession(session);
            String redisKey = getSessionKey(session.getId());
            if (StringUtil.isEmpty(redisManager.putJson(redisKey, json))) {
                logger.error("store session to redis failed");
                return false;
            }
            extendSession(redisKey);

            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean remove(String id) {
        try {
            Long result = redisManager.delete(getSessionKey(id));
            return result > 0;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String getSessionIdByUserId(Long userId) {
        String userSessionKey = getUserSessionKey(userId.toString());
        String oldSessionId = redisManager.getString(userSessionKey);
        return oldSessionId;
    }

    /**
     * 从redis中读取session
     *
     * @param id
     * @return
     */
    @Override
    public Session getSessionFromRedis(String id) {
        Session session = null;
        String json = redisManager.getString(getSessionKey(id));
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            session = deSerializeSession(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return session;
        }
    }

    /**
     * 得到session在redis中的key
     *
     * @param uuid
     * @return
     */
    private String getSessionKey(String uuid) {
        return B2B_SESSION_PREFIX + uuid;
    }

    /**
     * 得到用户关联的sessionid
     * @param userId
     * @return
     */
    private String getUserSessionKey(String userId){
            return B2B_USER_SESSION + userId;
    }

    /**
     * 序列化session
     *
     * @param session
     * @return
     */
    private String serializeSession(Session session) {
        if (session == null) return "";
        String json = JSON.toJSONString(session);
        return json;
    }

    /**
     * 反序列化session
     *
     * @param json
     * @return
     */
    private Session deSerializeSession(String json) {
        if (StringUtil.isEmpty(json)) return null;
        Session session = JSON.parseObject(json, DefaultSession.class);
        return session;
    }

    /**
     * 延长session寿命（目前只在登录的时候设置时间，后面考虑以一定的策略延长session失效时间）
     * @return
     */
    private boolean extendSession(String redisKey){
        Long result = redisManager.expire(redisKey, expireTime);
        return  result > 0;
    }

    @Override
    public Session getSessionById(String id) {
        Session session = null;
        String json = redisManager.getStringValue(getSessionKey(id));
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            session = deSerializeSession(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            return session;
        }
    }
}
