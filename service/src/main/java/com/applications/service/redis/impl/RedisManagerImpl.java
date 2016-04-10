package com.applications.service.redis.impl;

import com.alibaba.fastjson.JSON;
import com.applications.service.redis.intf.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisManagerImpl implements RedisManager {
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            log.error("redis 挂掉了", e);
        }
        return null;
    }

    public String getString(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String str = this.getString(key);
        if (null != str) {
            return (T) JSON.parseObject(str, clazz);
        }
        return null;
    }

    @Override
    public <T> T getObjectSilently(String key, Class<T> clazz) {
        try {
            return this.getObject(key, clazz);
        } catch (Exception e) {
            log.error("get object occur exception [key=" + key + ", clazz=" + clazz + "]", e);
        }
        return null;
    }

    @Override
    public <T> List<T> getObjectListSilently(String key, Class<T> clazz) {
        try {
            String str = this.getString(key);
            return JSON.parseArray(str, clazz);
        } catch (Exception e) {
            log.error("get object list occur exception [key=" + key + ", clazz=" + clazz + "]", e);
        }
        return null;
    }

    @Override
    public List<String> getStrings(List<String> keys) {
        Jedis jedis = getJedis();
        try {
            String[] toBeStored = keys.toArray(new String[keys.size()]);
            return jedis.mget(toBeStored);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String put(String key, String json, int second) {
        Jedis jedis = getJedis();
        try {
            return jedis.setex(key, second, json);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String putJson(String key, String json) {
        Jedis jedis = getJedis();
        try {
            return jedis.set(key, json);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    public Long delete(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.del(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    public boolean delete(List<String> keys) {
        Jedis jedis = getJedis();
        try {
            String[] toBeStored = keys.toArray(new String[keys.size()]);
            jedis.del(toBeStored);
            return true;
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return false;
        } finally {
            close(jedis);
        }
    }


    public boolean exists(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return false;
        } finally {
            close(jedis);
        }
    }

    @Override
    public void removeWithScore(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            jedis.zrem(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long addWithSet(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            return jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Collection<String> getZrangeByIndex(String key, int startIndex, int pageSize) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrange(key, startIndex, startIndex + pageSize - 1);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Collection<String> getZrangeByScore(String key, Double min, Double max) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Collection<String> getZrevrangeByIndex(String key, int startIndex, int pageSize) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrevrange(key, startIndex, startIndex + pageSize - 1);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Collection<String> getZRevrangeAll(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrangeByScore(key, "-inf", "+inf");
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long getZcard(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.zcard(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }


    @Override
    public Long hash(String key, String field, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String getHash(String key, String field) {
        Jedis jedis = getJedis();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Boolean hasHashByKey(String key, String field) {
        Jedis jedis = getJedis();
        try {
            return jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return false;
        } finally {
            close(jedis);
        }
    }


    @Override
    public Long delHash(String key, String field) {
        Jedis jedis = getJedis();
        try {
            return jedis.hdel(key, field);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public List<String> getHash(String key, String... field) {
        Jedis jedis = getJedis();
        try {
            return jedis.hmget(key, field);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Set<String> getAllHashKey(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Map<String, String> getAllHash(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String putHash(String key, Map<String, String> map) {

        Jedis jedis = getJedis();
        try {
            return jedis.hmset(key, map);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    public Long getIndexByMember(String key, String member) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrank(key, member);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public boolean setNX(String key, String value, int expire) {
        Jedis jedis = getJedis();
        try {
            Long result = jedis.setnx(key, value);
            if (result == 1l) {
                if(expire > 0) {
                    jedis.expire(key, expire);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return false;
        } finally {
            close(jedis);
        }
    }


    public Long expire(String key, int second) {
        Jedis jedis = getJedis();
        try {
            return jedis.expire(key, second);
        } catch (Exception e) {
            log.error("redis expire error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long addList(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String getListByIndex(String key, int index) {
        Jedis jedis = getJedis();
        try {
            return jedis.lindex(key, index);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long remList(String key, long count, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String lSet(String key, long count, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.lset(key, count, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }


    public Collection<String> getList(String key, int length) {
        Jedis jedis = getJedis();
        try {
            return jedis.lrange(key, 0, length);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long addSet(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long delSetByValue(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            return jedis.srem(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Set<String> getSet(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.incr(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long incr(String key, int expire) {
        Jedis jedis = getJedis();
        try {
            Long l= jedis.incr(key);
            jedis.expire(key,expire);
            return l;
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long ttlKey(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Boolean sismemberSet(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return false;
        } finally {
            close(jedis);
        }
    }

    @Override
    public String rankSet(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.srandmember(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    private void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        } catch (Exception e) {
            //ignore
        }
    }

    @Override
    public Collection<String> getKeys(String prefix) {
        Jedis jedis = getJedis();
        try {
            return jedis.keys(prefix+"*");
        } catch (Exception e) {
            log.error("redis error:", e);
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            return null;
        } finally {
            close(jedis);
        }
    }

    public String getStringValue(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } finally {
            close(jedis);
        }
    }


}
