package com.it.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
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
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
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
       
       //自定义拦截器,限制同一帐号同时在线的个数。
       Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
       filterMap.put("kickout", kickoutSessionControlFilter());
       shiroFilterFactoryBean.setFilters(filterMap);
       
       Map<String,String> filterChainDefinitionMap = new  LinkedHashMap<String,String>();
       // 配置不会被拦截的链接 相关静态资源
       //<!-- authc:所有url都必须登录认证通过才可以访问; anon:所有url都都可以匿名访问-->
       filterChainDefinitionMap.put("/bootstrap/**", "anon");
       filterChainDefinitionMap.put("/css/**", "anon");
       filterChainDefinitionMap.put("/icons/**", "anon");
       filterChainDefinitionMap.put("/images/**", "anon");
       filterChainDefinitionMap.put("/jquery-1.11.3/**", "anon");
       filterChainDefinitionMap.put("/js/**", "anon");
       filterChainDefinitionMap.put("/layui/**", "anon");
       filterChainDefinitionMap.put("/validate.so", "anon");
       filterChainDefinitionMap.put("/login/verfify", "anon");
       //登录、退出匿名访问
       filterChainDefinitionMap.put("/login/login", "anon");
       filterChainDefinitionMap.put("/login/logout", "anon");
       filterChainDefinitionMap.put("/login/signUp", "anon");
       filterChainDefinitionMap.put("/login/resetPwd", "anon");
       //所有url都必须认证通过才可以访问
       filterChainDefinitionMap.put("/**", "user,kickout");
//       filterChainDefinitionMap.put("/**", "authc");
       // 登录的路径
       shiroFilterFactoryBean.setLoginUrl("/login");
       // 登录成功后要跳转的链接
       shiroFilterFactoryBean.setSuccessUrl("/login/index");
       //未授权界面;
       shiroFilterFactoryBean.setUnauthorizedUrl("/common/403");
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
       securityManager.setCacheManager(ehCacheManager());
       //配置自定义session管理，使用ehcache 或redis
       securityManager.setSessionManager(sessionManager());
       //配置记住我cookie管理器
       securityManager.setRememberMeManager(rememberManager());
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
       enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
       //设置session缓存的名字 默认为 shiro-activeSessionCache
       enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
       //sessionId生成器
       enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
       return enterpriseCacheSessionDAO;
   }
   
   /**
    * 
    * @Title: rememberMeCookie    
    * @Description: TODO   记住我Cookie
    * @param @return
    * @return SimpleCookie
    * @throws
    */
   public SimpleCookie rememberMeCookie() {
	   //Cookie名称
	   SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
	   //记住我cookie生效时间1天 ,单位秒;
	   simpleCookie.setMaxAge(86400);
	   simpleCookie.setHttpOnly(true);
	   return simpleCookie;
   }
   
   /**
    * 
    * @Title: rememberManager    
    * @Description: TODO   将记住我cookie添加到CookieRememberMeManager
    * @param @return
    * @return CookieRememberMeManager
    * @throws
    */
   public CookieRememberMeManager rememberManager() {
	   CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	   cookieRememberMeManager.setCookie(rememberMeCookie());
	   //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)  
	   cookieRememberMeManager.setCipherKey(Base64.decode("S6Dvc7SNl0Zkh2e2WsODxw=="));
	   return cookieRememberMeManager;
   }
   
   /**
          * 配置保存sessionId的cookie 
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
   @Bean
   public EhCacheManager ehCacheManager() {
       EhCacheManager ehCacheManager = new EhCacheManager();
       ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
       return ehCacheManager;
   }
   
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
	    sessionManager.setCacheManager(ehCacheManager());
	    //全局会话超时时间（单位毫秒），默认30分钟，设置10分钟
//	    sessionManager.setGlobalSessionTimeout(600000);
	    //是否开启删除无效的session对象  默认为true
	    sessionManager.setDeleteInvalidSessions(true);
	    //是否开启定时调度器进行检测过期session 默认为true
	    sessionManager.setSessionValidationSchedulerEnabled(true);
	    //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
	    sessionManager.setSessionValidationInterval(300000);
	    //取消url 后面的 JSESSIONID
	    sessionManager.setSessionIdUrlRewritingEnabled(false);
       return sessionManager;
   } 
   
   /**
    * 限制同一账号登录同时登录人数控制
    * @return
    */
   public KickoutSessionControlFilter kickoutSessionControlFilter(){
      KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
      //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
      //shiro使用的redisManager()实现的cacheManager()缓存管理
      kickoutSessionControlFilter.setCacheManager(ehCacheManager());
      //用于根据会话ID，获取会话进行踢出操作的；
      kickoutSessionControlFilter.setSessionManager(sessionManager());
      //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
      kickoutSessionControlFilter.setKickoutAfter(false);
      //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
      kickoutSessionControlFilter.setMaxSession(1);
      //被踢出后重定向到的地址；
      kickoutSessionControlFilter.setKickoutUrl("/common/kickout");
       return kickoutSessionControlFilter;
    }
}
