package org.example.web.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTemplateDao {

	@Autowired
	private RedisTemplate redisTemplate;
	  
    public RedisTemplateDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;  
    }  
  
  
    @SuppressWarnings("rawtypes")
	public void set(String key, Object value) {  
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
  
        //BoundValueOperations的理解对保存的值做一些细微的操作  
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);  
    }  
    @SuppressWarnings("rawtypes")
    /**
     * expireTime单位是秒
     */
	public void set(String key, Object value,Long expireTime ) {  
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value); 
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);  
        //BoundValueOperations的理解对保存的值做一些细微的操作  
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);  
    }
  
    public Object get(String key) {  
        return redisTemplate.opsForValue().get(key);
    }  
  
    @SuppressWarnings("rawtypes")
	public void setList(String key, List<?> value) {  
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, value);  
    }  
  
    public List<?> getList(String key) {
        return (List<?>) redisTemplate.opsForList().leftPop(key);
    }  
  
    public void setSet(String key,  Object...  value) {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations. add(key, value);
    }
    public void setSetOne(String key, Object value) {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.intersect(key,value) ;
    }
  
    public Set<?> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }  
  
  
    public void setHash(String key, Map<String, ?> value) {  
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, value);
    }

    //修改hash里一个值
    public void setHashKey(String key, String hashKey, Object value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
    }

    public Object getHash(String key) {  
        return redisTemplate.opsForHash().entries(key);  
    }  
  
  
    public void delete(String key) {  
        redisTemplate.delete(key);  
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
        Set  keys = redisTemplate.keys(pattern);  
        if (keys.size() > 0)  
            redisTemplate.delete(keys);  
    } 
  

    /** 
     * 判断缓存中是否有对应的value 
     *  
     * @param key 
     * @return 
     */  
    public boolean exists(final String key) {  
        return redisTemplate.hasKey(key);  
    } 
    
    /** 
     * 根据通配符获取key集合
     *  
     * @param pattern 
     */  
    public Set getSetByPattern(final String pattern) {  
        Set  keys = redisTemplate.keys(pattern+"*");  
        return keys;
    }

    //给对应key递增
//    public void incrementByKey(String key,int cnt) {
//        redisTemplate.opsForValue().increment(key,cnt);
//    }

    //根据key获取有序集合所有元素
    public LinkedHashSet getSortedSet(String key) {
      return (LinkedHashSet) redisTemplate.opsForZSet().range(key,0,-1);
    }

    //添加有序集合,已有会覆盖
    public void addSortedSetScore(String key,int value,double score) {
        redisTemplate.opsForZSet().add(key,value,score);
    }

    //根据key和value,修改有序集合的分数，已有的分数会相加
    public void updateSortedSetScore(String key,int value,double score) {
        redisTemplate.opsForZSet().incrementScore(key,value,score);
    }
    //根据Key和value返回有序集合分数
    public Double getSortedSetScore(String key,int value ) {
        return redisTemplate.opsForZSet().score(key,value);
    }
}
