package org.zzq.farmproductplatform.config;
import org.zzq.farmproductplatform.config.UserResourceProperties;
import org.zzq.farmproductplatform.model.dao.CustomMapper;
import org.zzq.farmproductplatform.model.dao.UserMapper;
import org.zzq.farmproductplatform.model.entity.Privilege;
import org.zzq.farmproductplatform.model.entity.Role;
import org.zzq.farmproductplatform.model.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserResourceProperties userResourceProperties;

    @Autowired
    private CustomMapper customMapper;
    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles = customMapper.findRolesByUserId(user.getUserId());
        List<Privilege> privileges = new ArrayList<>();
        for (Role role : roles) {
            authorizationInfo.addRole(role.getCode());
            privileges = customMapper.findPrivilegesByRoleId(role.getRoleId());
            for (Privilege privilege : privileges) {
                authorizationInfo.addStringPermission(privilege.getCode());
            }
        }
//        for (Privilege privilege : privileges) {
//            System.out.println(privilege);
//        }
        return authorizationInfo;
    }

    /**
     * 认证方法
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("----------------------------");
        System.out.println("认证方法");
        System.out.println("----------------------------");
//        String username = (String) authenticationToken.getPrincipal();
//
//        Example example = new Example(User.class);
//        example.createCriteria().andEqualTo("username", username).andEqualTo("active", userResourceProperties.getActive());
//        List<User> users = userMapper.selectByExample(example);
//
//        if (users != null && users.size() != 0) {
//
//            User user = users.get(0);
//            AuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
//                    user,
//                    user.getPassword(),
//                    this.getName()
//            );
//            return simpleAuthenticationInfo;
//        }
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        User user = userMapper.selectUser(userToken.getUsername());
        if (user != null) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
        return null;
    }
}
