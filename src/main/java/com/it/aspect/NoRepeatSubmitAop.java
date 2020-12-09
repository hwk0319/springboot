package com.it.aspect;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.cache.Cache;
import com.it.util.Result;

/**
 * 
 * @ClassName: NoRepeatSubmitAop    
 * @Description: TODO  防止重复提交
 * @author hwk    
 * @date 2020年12月9日 上午10:48:25    
 *
 */
@Component
@Aspect
public class NoRepeatSubmitAop {
	public static final Logger log = LoggerFactory.getLogger(NoRepeatSubmitAop.class);
	
	@Autowired
	private Cache<String, Integer> cache;

	@Around("execution(* com.it.controller..*(..)) && @annotation(nrs)")
	public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit nrs){
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
			HttpServletRequest request;
			if(attributes != null) {
				request = attributes.getRequest();
				String key = sessionId + "-" + request.getServletPath();
				// 如果缓存中有这个url视为重复提交
				if(cache.getIfPresent(key) == null) {
					Object o = pjp.proceed();
					cache.put(key, 0);
					return o;
				}else {
					log.error("重复提交");
					return Result.repeatSubmit();
				}
			}
		} catch (Throwable e) {
			log.error("验证重复提交时出现未知异常！");
			log.error(e.getMessage());
		}
		return null;
	}
}
