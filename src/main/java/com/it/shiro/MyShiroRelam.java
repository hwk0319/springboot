package com.it.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.po.Permission;
import com.it.po.RoleInfo;
import com.it.po.UserInfo;
import com.it.service.systemManagement.UserService;
import com.it.util.MD5;
import com.it.util.SysConstant;

/**
 * 
 * @ClassName: MyShiroRelam  
 * @Description: TODO  实现授权与认证
 * @author Administrator  
 * @date 2019年7月11日  
 *
 */
public class MyShiroRelam extends AuthorizingRealm {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MyShiroRelam.class);
	
	@Autowired
    private UserService userService;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	Subject subject = SecurityUtils.getSubject();
        UserInfo user = (UserInfo)subject.getPrincipal();
        if(user != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 角色与权限字符串集合
            Collection<String> rolesCollection = new HashSet<>();
            Collection<String> premissionCollection = new HashSet<>();
			// 读取并赋值用户角色与权限
            Set<RoleInfo> roles = user.getRoles();
            for(RoleInfo role : roles){
                rolesCollection.add(role.getRole());
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions){
                	String per = permission.getPermission();
                	if(per != null) {
                		premissionCollection.add(per);
                	}
                }
                info.addStringPermissions(premissionCollection);
            }
            info.addRoles(rolesCollection);
            return info;
        }
        return null;
    }

    /**
     * 验证用户登录信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//    	logger.info("-->验证用户登录信息...");
        String username = (String)token.getPrincipal();
        //从数据库查询出User信息及用户关联的角色，权限信息，以备权限分配时使用
        UserInfo user = userService.findUserByName(username);
        if(null == user) {
        	logger.error("-->用户不存在...");
        	throw new UnknownAccountException();
        }
        if(SysConstant.USER_CANCEL.equals(user.getStatus())) {
        	logger.error("-->用户已注销...");
        	throw new DisabledAccountException();
        }
        if(SysConstant.USER_LOCK.equals(user.getStatus())) {
        	logger.error("-->用户已锁定...");
        	throw new LockedAccountException();
        }
        //根据role查询权限
        Set<Permission> Permission = userService.findPermissionByRoleid(user.getRoleId());
        RoleInfo role = new RoleInfo();
        role.setId(user.getRoleId());
        role.setRole(user.getRole());
        role.setPermissions(Permission);
        Set<RoleInfo> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        String pwd = user.getPassword();
        //MD5加密
        String md5pwd = MD5.GetMD5Code(pwd);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            user, //用户名
            md5pwd, //密码
            getName()  //realm name
       );
        return authenticationInfo;
    }
}
