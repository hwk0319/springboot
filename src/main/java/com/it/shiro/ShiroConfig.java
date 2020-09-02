package com.it.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * 
 * @ClassName: ShiroConfig  
 * @Description: TODO  Shiro 配置
 * @author Administrator  
 * @date 2019年7月11日  
 *
 */
@Configuration
public class ShiroConfig {
	private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
	public ShiroConfig(){
		logger.info("ShiroConfig init ....");
    }

	/**
	 * 
	 * @Title: ShiroFilterFactoryBean  
	 * @Description: TODO  shiro过滤器配置
	 * @param @param securityManager
	 * @param @return    参数  
	 * @return ShiroFilterFactoryBean    返回类型  
	 * @throws
	 */
   @Bean
   public ShiroFilterFactoryBean ShiroFilterFactoryBean (SecurityManager securityManager) {
//	   logger.info("ShiroConfiguration.shirFilter----");
       ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
       shiroFilterFactoryBean.setSecurityManager(securityManager);
       Map<String,String> filterChainDefinitionMap = new  LinkedHashMap<String,String>();
       // 配置不会被拦截的链接 相关静态资源
       //<!-- authc:所有url都必须登录认证通过才可以访问; anon:所有url都都可以匿名访问-->
       filterChainDefinitionMap.put("/bootstrap/**", "anon");
       filterChainDefinitionMap.put("/css/**", "anon");
       filterChainDefinitionMap.put("/images/**", "anon");
       filterChainDefinitionMap.put("/jquery-1.11.3/**", "anon");
       filterChainDefinitionMap.put("/js/**", "anon");
       filterChainDefinitionMap.put("/layui/**", "anon");
       //登录、退出匿名访问
       filterChainDefinitionMap.put("/login/login", "anon");
       filterChainDefinitionMap.put("/login/logout", "anon");
       filterChainDefinitionMap.put("/login/signUp", "anon");
       filterChainDefinitionMap.put("/login/resetPwd", "anon");
       //所有url都必须认证通过才可以访问
       filterChainDefinitionMap.put("/**", "authc");
       // 登录的路径
       shiroFilterFactoryBean.setLoginUrl("/login");
       // 登录成功后要跳转的链接
       shiroFilterFactoryBean.setSuccessUrl("/login/index");
       //未授权界面;
       shiroFilterFactoryBean.setUnauthorizedUrl("/commom/403");
       shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
       return shiroFilterFactoryBean;
   }
   /**
    * 加密方式配置
    */
   @Bean
   public HashedCredentialsMatcher hashedCredentialsMatcher(){
       HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
       hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
       hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
       return hashedCredentialsMatcher;
   }

	 /**
	     * 认证器配置
	  */
   @Bean
   public MyShiroRelam myShiroRealm(){
       MyShiroRelam myShiroRelam = new MyShiroRelam();
       myShiroRelam.setCredentialsMatcher(hashedCredentialsMatcher());
       return myShiroRelam;
   }

   /**
          * 安全管理器配置
    */
   @Bean
   public SecurityManager securityManager(){
       DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
       //设置自定义realm.
       securityManager.setRealm(myShiroRealm());
       //配置 ehcache缓存管理器
//       securityManager.setCacheManager(ehCacheManager());
       //配置自定义session管理，使用ehcache 或redis
       securityManager.setSessionManager(sessionManager());
       return securityManager;
   }

   /**
    * 开启@RequirePermission注解的配置，要结合DefaultAdvisorAutoProxyCreator一起使用，或者导入aop的依赖
    */
   @Bean
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
       AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
       authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
       return authorizationAttributeSourceAdvisor;
   }

   /**
    * 定义Spring MVC的异常处理器
    */
   @Bean
   public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
       SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
       Properties mappings = new Properties();
       mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
       mappings.setProperty("UnauthorizedException","403");//处理shiro的认证未通过异常
       r.setExceptionMappings(mappings);  // None by default
       r.setDefaultErrorView("error");    // No default
       r.setExceptionAttribute("exception");     // Default is "exception"
       return r;
   }
   
   /**
    * 配置session监听
    * @return
    */
   @Bean("sessionListener")
   public ShiroSessionListener sessionListener(){
       ShiroSessionListener sessionListener = new ShiroSessionListener();
       return sessionListener;
   }
   
   /**
    * 配置会话ID生成器
    * @return
    */
   @Bean
   public SessionIdGenerator sessionIdGenerator() {
       return new JavaUuidSessionIdGenerator();
   }
   
   /**
    * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
    * MemorySessionDAO 直接在内存中进行会话维护
    * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
    * @return
    */
   @Bean
   public SessionDAO sessionDAO() {
       EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
       //使用ehCacheManager
//       enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
       //设置session缓存的名字 默认为 shiro-activeSessionCache
       enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
       //sessionId生成器
       enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
       return enterpriseCacheSessionDAO;
   }
   
   /**
    * 配置保存sessionId的cookie 
    * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
    * @return
    */
   @Bean("sessionIdCookie")
   public SimpleCookie sessionIdCookie(){
       //这个参数是cookie的名称
       SimpleCookie simpleCookie = new SimpleCookie("sid");
       //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
       //设为true后，只能通过http访问，javascript无法访问
       //防止xss读取cookie
       simpleCookie.setHttpOnly(true);
       simpleCookie.setPath("/");
       //maxAge=-1表示浏览器关闭时失效此Cookie
       simpleCookie.setMaxAge(-1);
       return simpleCookie;
   }
   
   /**
    * 开启缓存
    * shiro-ehcache实现
    * @return
    */
//   @Bean
//   public EhCacheManager ehCacheManager() {
//       logger.info("ShiroConfiguration.getEhCacheManager()");
//       EhCacheManager ehCacheManager = new EhCacheManager();
//       ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
//       return ehCacheManager;
//   }
   
   /**
    * 配置会话管理器，设定会话超时及保存
    * @return
    */
   @Bean("sessionManager")
   public SessionManager sessionManager() {
	    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	    Collection<SessionListener> listeners = new ArrayList<SessionListener>();
	    //配置监听
	    listeners.add(sessionListener());
	    sessionManager.setSessionListeners(listeners);
	    sessionManager.setSessionIdCookie(sessionIdCookie());
	    sessionManager.setSessionDAO(sessionDAO());
//	    sessionManager.setCacheManager(ehCacheManager());

	    //全局会话超时时间（单位毫秒），默认30分钟，设置10分钟
	    sessionManager.setGlobalSessionTimeout(600000);
	    //是否开启删除无效的session对象  默认为true
	    sessionManager.setDeleteInvalidSessions(true);
	    //是否开启定时调度器进行检测过期session 默认为true
	    sessionManager.setSessionValidationSchedulerEnabled(true);
	    //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
//	    sessionManager.setSessionValidationInterval(60000);
	    //取消url 后面的 JSESSIONIsD
	    sessionManager.setSessionIdUrlRewritingEnabled(false);
       return sessionManager;
   } 
}
