package com.applications.service.session.intf;

public interface SessionDoer {

    ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();

    /**
     * 得到用户id
     * @param sessionId
     * @return
     */
    Long getUserId(String sessionId);

    /**
     * 得到登录名
     * @param sessionId
     * @return
     */
    String getLoginName(String sessionId);

    /**
     * 得到登录名
     * @param sessionId
     * @return
     */
    Object getAttribute(String sessionId, String key);

    /**
     * 得到用户id
     * @param session
     * @return
     */
    Long getUserId(Session session);

    /**
     * 得到登录名
     * @param session
     * @return
     */
    String getLoginName(Session session);

    /**
     * 得到登录名
     * @param session
     * @return
     */
    Object getAttribute(Session session, String key);

}
