package com.applications.service.session.impl;

import com.applications.service.session.intf.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by yida on 16/1/13.
 */
public class DefaultSession implements Session, Serializable {

	private static final long serialVersionUID = 6294354616416758926L;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String id;
	private HashMap<String, Object> attrs = new HashMap<String, Object>();
	private long lastActiveTime;
	private boolean isNew = true;

	public DefaultSession() {
	}

	public DefaultSession(String id) {
		if (StringUtils.isBlank(id))
			throw new IllegalArgumentException("id is blank");
		this.id = id;
		lastActiveTime = new Date().getTime();
	}

	@Override
	public void setAttribute(String key, Object value) {
		if (StringUtils.isBlank(key))
			throw new IllegalArgumentException("key is blank.");
		attrs.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		if (StringUtils.isBlank(key))
			throw new IllegalArgumentException("key is blank.");
		return attrs.get(key);
	}

	@Override
	public boolean existAttribute(String key) {
		if (StringUtils.isBlank(key))
			throw new IllegalArgumentException("key is blank.");
		return attrs.containsKey(key);
	}

	@Override
    public void removeAttribute(String key) {
	    if (StringUtils.isBlank(key))
            throw new IllegalArgumentException("key is blank.");
	    attrs.remove(key);
    }

    @Override
	public String getId() {
		return id;
	}

	@Override
	public long getLastActiveTime() {
		return lastActiveTime;
	}

	@Override
	public void update(long time) {
		this.lastActiveTime = time;
		isNew = false;
	}


	@Override
    public HashMap<String, Object> getAttrs() {
        return this.attrs;
    }
	
    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

	public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    public void setAttrs(HashMap<String, Object> attrs) {
        this.attrs = attrs;
    }

    @Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("attrs", attrs)
				.append("lastActiveTime", SDF.format(new Date(lastActiveTime))).toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
}
