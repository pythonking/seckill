package com.kaishengit.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * redis工具类
 *
 * @author king
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
            log.error("方法 setStringValue 出现异常，key={}，value={}", key, value);
            return false;
        } finally {
            closeJedis(jedis);
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
            log.error("方法 lpush getStringValue，key={}", key);
            return null;
        } finally {
            closeJedis(jedis);
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
            log.error("方法 setMapValue 出现异常，key={}", key);
            return false;
        } finally {
            closeJedis(jedis);
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
            log.error("方法 getMapValue 出现异常，key={}", key);
            return null;
        } finally {
            closeJedis(jedis);
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
            log.error("方法 lpush 出现异常，key={}", key);
            return false;
        } finally {
            closeJedis(jedis);
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
            closeJedis(jedis);
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
            log.error("方法 lpush 出现异常，key={}，value={}", key);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 集合中弹出
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        Jedis jedis = pool.getResource();
        try {
            String value = jedis.lpop(key);
            return value;
        } catch (Exception e) {
            log.error("方法 lpush 出现异常，key={}", key);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 队列中左加入
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lpush(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            Long length = jedis.lpush(key, value);
            return length;
        } catch (Exception e) {
            log.error("方法 lpush 出现异常，key={}，value={}", key, value);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 队列中右加入
     *
     * @param key
     * @param value
     * @return
     */
    public static Long rpush(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            Long length = jedis.rpush(key, value);
            return length;
        } catch (Exception e) {
            log.error("方法 rpush 出现异常，key={}，value={}", key, value);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取集合长度
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {
        Jedis jedis = pool.getResource();
        try {
            Long length = jedis.llen(key);
            return length;
        } catch (Exception e) {
            log.error("方法 llen 出现异常，key={}，value={}", key);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    private static void closeJedis(Jedis jedis) {
        // 使用完后，将连接放回连接池
        if (null != jedis) {
            jedis.close();
        }
    }
}
