package com.it.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @ClassName: RedisUtil    
 * @Description: TODO  
 * @author hwk    
 * @date 2021年1月8日 上午10:11:20    
 *
 */
@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 
	 * @Title: expire    
	 * @Description: TODO   指定缓存失效时间
	 * @param @param key
	 * @param @param time
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean expire(String key, long time) {
		try {
			if(time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: getExpire    
	 * @Description: TODO   根据key 获取过期时间
	 * @param @param key
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}
	
	/**
	 * 
	 * @Title: hasKey    
	 * @Description: TODO   判断key是否存在
	 * @param @param key
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: del    删除一个或多个缓存
	 * @Description: TODO   
	 * @param @param key
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if(key != null && key.length > 0) {
			if(key.length == 1) {
				redisTemplate.delete(key[0]);
			}else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
	
	/**
	 * 
	 * @Title: get    
	 * @Description: TODO   普通缓存获取
	 * @param @param key
	 * @param @return
	 * @return Object
	 * @throws
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 
	 * @Title: set    
	 * @Description: TODO   普通缓存放入
	 * @param @param key
	 * @param @param value
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: set    
	 * @Description: TODO   普通缓存放入并设置时间
	 * @param @param key
	 * @param @param value
	 * @param @param time 要大于0 如果time小于等于0 将设置无限期
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if(time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: incr    
	 * @Description: TODO   递增 ，要增加几(大于0)
	 * @param @param key
	 * @param @param delta
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long incr(String key, long delta) {
		if(delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/**
	 * 
	 * @Title: decr    
	 * @Description: TODO   递减，要减少几(小于0)
	 * @param @param key
	 * @param @param delta
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long decr(String key, long delta) {
		if(delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/**
	 * 
	 * @Title: hget    
	 * @Description: TODO   获取hash缓存
	 * @param @param key
	 * @param @param item
	 * @param @return
	 * @return Object
	 * @throws
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}
	
	/**
	 * 
	 * @Title: hmget    
	 * @Description: TODO  获取hashKey对应的所有键值 
	 * @param @param key
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<Object, Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 
	 * @Title: hmset    
	 * @Description: TODO  将hash放入缓存
	 * @param @param key
	 * @param @param map
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: hmset    
	 * @Description: TODO   将hash放入缓存，并设置时间
	 * @param @param key
	 * @param @param map
	 * @param @param time
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: hset    
	 * @Description: TODO   向一张hash表中放入数据,如果不存在将创建
	 * @param @param key
	 * @param @param item
	 * @param @param value
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: hset    
	 * @Description: TODO   向一张hash表中放入数据,如果不存在将创建
	 * @param @param key
	 * @param @param item
	 * @param @param value
	 * @param @param time 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: hdel    
	 * @Description: TODO   删除hash表中的值
	 * @param @param key
	 * @param @param item
	 * @return void
	 * @throws
	 */
	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}
	
	/**
	 * 
	 * @Title: hasHashkey    
	 * @Description: TODO  判断hash表中是否有该项的值 
	 * @param @param key
	 * @param @param item
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean hasHashkey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}
	
	/**
	 * 
	 * @Title: hincr    
	 * @Description: TODO hash递增 如果不存在,就会创建一个 并把新增后的值返回  
	 * @param @param key
	 * @param @param item
	 * @param @param delta
	 * @param @return
	 * @return double
	 * @throws
	 */
	public double hincr(String key, String item, double delta) {
		return redisTemplate.opsForHash().increment(key, item, delta);
	}
	
	/**
	 * 
	 * @Title: hdecr    
	 * @Description: TODO   hash递减， 如果不存在,就会创建一个 并把新增后的值返回  
	 * @param @param key
	 * @param @param item
	 * @param @param delta
	 * @param @return
	 * @return double
	 * @throws
	 */
	public double hdecr(String key, String item, double delta) {
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}
	
	/**
	 * 
	 * @Title: sGet    
	 * @Description: TODO   根据key获取Set中的所有值
	 * @param @param key
	 * @param @return
	 * @return Set<Object>
	 * @throws
	 */
	public Set<Object> sGet(String key){
		return redisTemplate.opsForSet().members(key);
	}
	
	/**
	 * 
	 * @Title: sSet    
	 * @Description: TODO   将数据放入set缓存
	 * @param @param key
	 * @param @param values 值 可以是多个
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @Title: sHasKey    
	 * @Description: TODO   根据value从一个set中查询,是否存在
	 * @param @param key
	 * @param @param value
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean sHasKey(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}
	
	/**
	 * 
	 * @Title: sSetAndTime  将set数据放入缓存
	 * @Description: TODO   
	 * @param @param key
	 * @param @param time  过期时间
	 * @param @param values
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if(time > 0) {
				expire(key, time);
			}
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @Title: sGetSetSize    
	 * @Description: TODO   获取set缓存的长度
	 * @param @param key
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @Title: setRemove    
	 * @Description: TODO   移除值为value的
	 * @param @param key
	 * @param @param objects
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long setRemove(String key, Object... objects ) {
		try {
			return redisTemplate.opsForSet().remove(key, objects);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @Title: lGet    
	 * @Description: TODO   获取list缓存的内容
	 * @param @param key
	 * @param @param start
	 * @param @param end
	 * @param @return
	 * @return List<Object>
	 * @throws
	 */
	public List<Object> lGet(String key, long start, long end){
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: lGetListSize    
	 * @Description: TODO   获取list缓存的长度
	 * @param @param key
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @Title: lGetListByIndex    
	 * @Description: TODO   通过索引 获取list中的值
	 * @param @param key
	 * @param @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @param @return
	 * @return Object
	 * @throws
	 */
	public Object lGetListByIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: lSet    
	 * @Description: TODO   将list放入缓存
	 * @param @param key
	 * @param @param value
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: lSet    将list放入缓存，并设置过期时间
	 * @Description: TODO   
	 * @param @param key
	 * @param @param value
	 * @param @param time
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if(time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: lSet    
	 * @Description: TODO   将list集合放入缓存
	 * @param @param key
	 * @param @param list
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean lSet(String key, List<Object> list) {
		try {
			redisTemplate.opsForList().rightPushAll(key, list);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: lSet    
	 * @Description: TODO   将list集合放入缓存，并设置过期时间
	 * @param @param key
	 * @param @param list
	 * @param @param time
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean lSet(String key, List<Object> list, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, list);
			if(time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: lUpdateByIndex    
	 * @Description: TODO   根据索引修改list中的某条数据
	 * @param @param key
	 * @param @param value
	 * @param @param index
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean lUpdateByIndex(String key, Object value, long index) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: lRemove    
	 * @Description: TODO   移除N个值为value
	 * @param @param key
	 * @param @param count
	 * @param @param value
	 * @param @return
	 * @return long
	 * @throws
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			return redisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
