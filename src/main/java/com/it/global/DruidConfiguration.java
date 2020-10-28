package com.it.global;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * @ClassName: DruidConfiguration  
 * @Description: TODO  druid配置
 * @author Administrator  
 * @date 2020年9月2日  
 *
 */
@Configuration
public class DruidConfiguration {
	
	@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return new DruidDataSource();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public ServletRegistrationBean druidStatViewServlet() {
        //ServletRegistrationBean提供类的进行注册
		ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "");
        //IP黑名单（同时存在时，deny优先于allow）
        //如果满足deny，就提示：sorry，you are not permitted to view this page
        servletRegistrationBean.addInitParameter("deny", "");
        //登录查看信息的账号和密码
//        servletRegistrationBean.addInitParameter("loginUsername", "admin");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean druidStatFilter(DruidStatProperties properties) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png, *.css,*.ico,/druid/*");
        return filterRegistrationBean;

    }
}
