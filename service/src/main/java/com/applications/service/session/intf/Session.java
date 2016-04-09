package com.applications.service.session.intf;

import java.util.HashMap;


public interface Session {

	String getId();

	void setAttribute(String key, Object value);

	Object getAttribute(String key);
	
	void removeAttribute(String key);

	boolean existAttribute(String key);
	
	void setLastActiveTime(long lastActiveTime);
	
	void setAttrs(HashMap<String, Object> attrs);
	
	HashMap<String, Object> getAttrs();

	long getLastActiveTime();

	void update(long time);
}
