package com.kaishengit.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * redis缓存的工具类
 */
public class RedisUtil {
    private static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * redis连接池对象
     */
    private static JedisPool pool;

    public static JedisPool getPool() {
        return pool;
    }

    public static void setPool(JedisPool pool) {
        RedisUtil.pool = pool;
    }

    /**
     * 放入redis缓存：字符串
     *
     * @param key
     * @param value
     * @param seconds :过期时间（秒）
     */
    public static boolean setStringValue(String key, String value, int seconds) {
        Jedis jedis = pool.getResource();
        try {
            jedis.setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            log.error("放入字符串到redis缓存出现异常，key={},异常信息：{}", key, e);
            return false;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 从redis缓存中获取值：字符串
     *
     * @param key
     * @return
     */
    public static String getStringValue(String key) {
        Jedis jedis = pool.getResource();
        try {
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            log.error("从redis缓存取字符串值出现异常，key={}", key);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 放入redis缓存：Map集合
     *
     * @param key
     * @param hashMap
     */
    public static boolean setMapValue(String key, Map<String, String> hashMap) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, hashMap);
            return true;
        } catch (Exception e) {
            log.error("放入Map集合到redis缓存出现异常，key={}", key);
            return false;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 从redis缓存中获取值：Map集合
     *
     * @param key    :map存到redis中的key,eg:userMap
     * @param fields :map集合中的key，eg:username、password
     * @return
     */
    public static List<String> getMapValue(String key, String... fields) {
        Jedis jedis = pool.getResource();
        try {
            List<String> value = jedis.hmget(key, fields);
            return value;
        } catch (Exception e) {
            log.error("从redis缓存取字符串值出现异常，key={}", key);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 放入redis缓存：Object对象
     *
     * @param key
     * @param obj
     * @return
     */
    public static boolean setObject(String key, Object obj) {
        Jedis jedis = pool.getResource();
        try {
            // 序列化后放入
            jedis.set(key.getBytes(), SerializationUtil.serialize(obj));
            return true;
        } catch (Exception e) {
            log.error("放入对象到redis缓存出现异常，key={}", key);
            return false;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 放入redis缓存：Object对象
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            // 序列化后放入
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            log.error("方法 setString 出现异常，key={}", key);
            return false;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 从redis缓存中获取值：Object对象
     *
     * @param t   ：对象的类型
     * @param key
     * @return
     */
    public static <T> T getObject(T t, String key) {
        Jedis jedis = pool.getResource();
        try {
            byte[] objBytes = jedis.get(key.getBytes());
            if (objBytes == null || objBytes.length <= 0) {
                return null;
            } else {
                // 反序列化成对象
                @SuppressWarnings("unchecked")
                T obj = (T) SerializationUtil.deserialize(objBytes);
                return obj;
            }
        } catch (Exception e) {
            log.error("从redis缓存取字符串值出现异常，key={}", key);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public static String lpop(String key) {
        Jedis jedis = pool.getResource();
        try {
            String value = jedis.lpop(key);
            return value;
        } catch (Exception e) {
            log.error("从redis 移除字符串值出现异常，key={}", key);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public static Long lpush(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            Long length = jedis.lpush(key, value);
            return length;
        } catch (Exception e) {
            log.error("从redis 移除字符串值出现异常，key={}，value={}", key, value);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public static Long llen(String key) {
        Jedis jedis = pool.getResource();
        try {
            Long length = jedis.llen(key);
            return length;
        } catch (Exception e) {
            log.error("方法 llen 出现异常，key={}，value={}", key);
            return null;
        } finally {
            // 使用完后，将连接放回连接池
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
