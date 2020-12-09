package com.it.aspect;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**   
 * @ClassName: UrlCache    
 * @Description: TODO  内存缓存，防止重复提交数据使用
 * @author hwk    
 * @date 2020年12月9日 上午10:40:58    
 *     
 */
@Configuration
public class UrlCache {

	/**
	 * 
	 * @Title: getCache    
	 * @Description: TODO   // 缓存有效期为5秒
	 * @param @return  
	 * @return Cache<String,Integer> 
	 * @throws
	 */
	@Bean
	public Cache<String, Integer> getCache() {
		return CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.SECONDS).build();
	}
}
