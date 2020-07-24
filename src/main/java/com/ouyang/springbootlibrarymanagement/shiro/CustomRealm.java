package com.ouyang.springbootlibrarymanagement.shiro;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysPermissions;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysPermissionsService;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysRoleService;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysPermissionsService sysPermissionsService;

    /**
     * 重写此方法避免shiro报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //实现权限认证，通过服务加载用户角色和权限信息设置进去
        String username = (String) principalCollection.getPrimaryPrincipal();
        SysUserEntity user = sysUserService.getUserByUsername(username);
        if (user != null) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            List<SysRoleEntity> roleList = sysRoleService.getRolesByUser(user);
            for (SysRoleEntity role : roleList) {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                List<SysPermissions> permissionsList = sysPermissionsService.getPermissionsByRole(role);
                for (SysPermissions permissions : permissionsList) {
                    simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
                }
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //实现用户认证
        String token = (String) authenticationToken.getCredentials();
        if (token == null){
            throw  new AuthenticationException("token为空！");
        }
        if (!JwtUtils.checkToken(token)){
            throw  new AuthenticationException("token失效！");
        }
        String username = JwtUtils.getUserNameByToken(token);
        SysUserEntity user = sysUserService.getUserByUsername(username);
        if (user == null){
            throw  new AuthenticationException("token非法！");
        }
        Integer status = user.getStatus();
        if (status == 0){
            throw  new AuthenticationException("用户已被禁用");
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,token,getName());
        return simpleAuthenticationInfo;
    }
}
