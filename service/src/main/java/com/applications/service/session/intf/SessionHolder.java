package com.applications.service.session.intf;

import java.util.HashMap;

public interface SessionHolder {
    /**
     * 获取会话对象。
     * 如果已经存在则直接返回，否则返回为<code>null</code>。
     *
     * @param id
     * @return
     */
    Session getSession(String id);

    /**
     * 创建session，id可为空，则自动创建sessinId
     * @param id
     * @return
     */
    Session createSession(String id, HashMap<String, Object> attrs);

    /**
     * 替换session（如踢人）
     * @param sessionId
     * @param userId
     * @return
     */
    boolean replaceSession(String sessionId, String userId);

    /**
     * 塞入一个sessin
     * @param session
     */
    boolean put(Session session);

    /**
     * 移除session
     * @param id
     */
    boolean remove(String id);

    /**
     * 利用userId得到sessionId
     * @param userId
     * @return
     */
    String getSessionIdByUserId(Long userId);

    Session getSessionFromRedis(String id);

    /**
     * 通过sessionId获取session，去掉了catch处理
     * @param id
     * @return
     */
    Session getSessionById(String id);
}
