package org.example.web.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;


//专门用于shiro操作redis
@Component
@Slf4j
public class ShiroRedisTemplateDao {
    @Autowired
    private RedisTemplate shiroRedisTemplate;




    @SuppressWarnings("rawtypes")
    public void set(String key, Object value) {
        ValueOperations valueOperations = shiroRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * expireTime单位是秒
     */
    public void set(String key, Object value,Long expireTime ) {
        ValueOperations valueOperations = shiroRedisTemplate.opsForValue();
        valueOperations.set(key, value);
        shiroRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        //BoundValueOperations的理解对保存的值做一些细微的操作
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
    }

    public Object get(String key) {
        return shiroRedisTemplate.opsForValue().get(key);
    }


    public void delete(String key) {
        shiroRedisTemplate.delete(key);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void delete(final String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void deletePattern(final String pattern) {
        Set keys = shiroRedisTemplate.keys(pattern);
        if (keys.size() > 0)
            shiroRedisTemplate.delete(keys);
    }

    /**
     * 根据通配符获取key集合
     *
     * @param pattern
     */
    public Set getSetByPattern(final String pattern) {
        Set  keys = shiroRedisTemplate.keys(pattern+"*");
        return keys;
    }

}
