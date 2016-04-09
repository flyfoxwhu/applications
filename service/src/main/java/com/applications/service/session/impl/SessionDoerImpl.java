package com.applications.service.session.impl;

import com.applications.service.session.intf.Session;
import com.applications.service.session.intf.SessionDoer;
import com.applications.service.session.intf.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sessionDoer")
public class SessionDoerImpl implements SessionDoer {

    @Autowired
    private SessionHolder sessionHolder;

    private static Logger logger = LoggerFactory.getLogger(SessionDoerImpl.class);

    @Override
    public Long getUserId(String sessionId) {
        Session session = sessionHolder.getSession(sessionId);
        if(session == null) return null;
        try {
            String str = convertObj2String(getAttribute(session, "userId"));
            Long userId = Long.valueOf(str);
            return userId;
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String getLoginName(String sessionId) {
        Session session = getSession(sessionId);
        return convertObj2String(getLoginName(session));
    }

    @Override
    public Object getAttribute(String sessionId, String key) {
        Session session = getSession(sessionId);
        return getAttribute(session, key);
    }

    @Override
    public Long getUserId(Session session) {
        if(session == null) return null;
        try {
            String str = convertObj2String(getAttribute(session, "userId"));
            Long userId = Long.valueOf(str);
            return userId;
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String getLoginName(Session session) {
        if(session == null) return null;
        try {
            return convertObj2String(getAttribute(session, "loginName"));
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public Object getAttribute(Session session, String key) {
        if(session == null) return null;
        try {
            if (session.existAttribute(key)) {
                return session.getAttribute(key);
            }
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    private Session getSession(String sessionId){
        /*if(SessionDoer.threadLocalSession.get() != null){
            return SessionDoer.threadLocalSession.get();
        }*/
        return sessionHolder.getSession(sessionId);
    }
    /**
     * obj转string
     * @param obj
     * @return
     */
    private String convertObj2String(Object obj){
        if(obj == null) return  "";
        return  obj.toString();
    }
}
