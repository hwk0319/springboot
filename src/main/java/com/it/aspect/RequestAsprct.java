package com.it.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.aspectj.lang.reflect.MethodSignature;

import com.it.po.OperationLogs;
import com.it.po.UserInfo;
import com.it.service.logManagement.OperationLogsService;

/**
 * 
 * @ClassName: RequestAsprct  日志实现
 * @Description: TODO  发送请求的切面入口类,需要在Spring中注入
 * @author Administrator  
 * @date 2019年7月25日  
 *
 */
@Component
@Aspect
public class RequestAsprct {
	private static final Logger logger = LoggerFactory.getLogger(RequestAsprct.class);
	     
	     @Resource(name = "operationLogsService")
	     private OperationLogsService service;
	     
	     /**
	      * 记录日志,定义切入点:指定那些业务(业务对应的方法)
	      * @Title: 切入点说明:在注解处进行切入
	      * @date 2019年7月28日
	      * @return void
	      */
	     @Pointcut("@annotation(com.it.aspect.OperLogs)")
	     public void OperLogs() {
	         
	     }
	     
	     /**
	      * 
	      * @Title: doBefore  
	      * @Description: TODO  在切入点开始处(方法执行前)切入内容
	      * @param @param joinPoint    参数  
	      * @return void    返回类型  
	      * @throws
	      */
	     @Before("OperLogs()")
	     public void doBefore(JoinPoint joinPoint) {
	    	 //从切面织入点处通过反射机制获取织入点处的方法
	         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	         //获取切入点所在的方法
	         Method method1 = signature.getMethod();
	         //获取操作
	         OperLogs myLog = method1.getAnnotation(OperLogs.class);
	         
	         ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	         HttpServletRequest request = servletRequestAttributes.getRequest();
	         
	         UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
	         String username = user.getName();//用户名
	         String requestUri = request.getRequestURI();// 得到请求的资源
	         String remoteAddr = request.getRemoteAddr();// 得到来访者的IP地址
	         String reqmethod = request.getMethod();// 得到请求URL地址时使用的方法
	         String remoteHost = request.getRemoteHost();//客户端主机名
	         String className = joinPoint.getSignature().getDeclaringTypeName();//类名
	         String methodName = joinPoint.getSignature().getName();//方法名
	         
	         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         OperationLogs bean = new OperationLogs();
	         bean.setUserName(username);
	         bean.setIp(remoteAddr);
	         bean.setCreateDate(df.format(new Date()));
	         bean.setMethodName(methodName);
	         bean.setRemoteHost(remoteHost);
	         bean.setClassName(className);
	         bean.setUrl(requestUri);
	         bean.setRequestMethod(reqmethod);
	         if (myLog != null) {
	             String value = myLog.value();
	             bean.setOperation(value);//保存获取的操作
	         }
	         
	         //TODO :测试直接入库操作，用户量比较大的网站一般缓存到消息队列执行批量入库,推荐Apache Kafka
	         service.addOperationLogs(bean);
	     }
	     
	     /**
	      * 
	      * @Title: doAfter  
	      * @Description: TODO  在切入点结尾处(方法执行后)切入内容
	      * @param @param joinPoint    参数  
	      * @return void    返回类型  
	      * @throws
	      */
	     @After("OperLogs()")
	     public void doAfter(JoinPoint joinPoint) {
	    	 
	     }
	     
	     /**
	      * 
	      * @Title: doAfterReturning  
	      * @Description: TODO  在切入点return内容之后切入内容
	      * @param @param result    参数  
	      * @return void    返回类型  
	      * @throws
	      */
	     @AfterReturning(returning = "result",pointcut = "OperLogs()")
	     public void doAfterReturning(Object result) {

	     }
}
