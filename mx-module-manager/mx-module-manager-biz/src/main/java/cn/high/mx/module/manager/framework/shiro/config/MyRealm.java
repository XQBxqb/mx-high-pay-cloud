package cn.high.mx.module.manager.framework.shiro.config;


import cn.high.mx.framework.common.exception.BizException;
import cn.high.mx.framework.common.exception.enums.BaseStatusEnum;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.module.manager.framework.shiro.core.BaseShiroService;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;
import cn.high.mx.module.manager.framework.shiro.core.consts.JwtConsts;
import cn.high.mx.module.manager.framework.shiro.core.jwt.JWTCredentialsMatcher;
import cn.high.mx.module.manager.framework.shiro.core.jwt.JwtToken;
import cn.high.mx.module.manager.framework.shiro.core.util.JwtUtils;
import cn.high.mx.module.manager.utils.local.ThreadLocalData;
import javafx.util.Pair;
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

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private BaseShiroService shiroService;

    // 指定凭证匹配器。匹配器工作在认证后，授权前。
    public MyRealm() {
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }
    
    // 判断token是否为JWTToken 必须重写
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("AuthenticationInfo 开始认证");
        String token = ((JwtToken) authenticationToken).getToken();
        String username = JwtUtils.parseToken(token, JwtConsts.JWT_TOKEN_KEY);
        BaseAdminUser adminUser = shiroService.getAdminUserByName(username);
        if(adminUser == null)
            throw new BizException(BizStatusEnum.RES_TOKEN_ERROR);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                adminUser,
                token,
                "myRealm"
        );
        return simpleAuthenticationInfo;
    }
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始授权...");
        // 从PrincipalCollection获取user
        BaseAdminUser adminUser = (BaseAdminUser) principalCollection.getPrimaryPrincipal();
        String role = adminUser.getRole();
        List<BaseRole> rolesList= shiroService.getRoleIdByRoleName(role);
        List<BasePermission> permissions = rolesList.stream()
                                                    .flatMap(t -> shiroService.getPermissionByRole(t.getId())
                                                                              .stream())
                                                    .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<permissions.size();i++){
            stringBuilder.append(permissions.get(i).getId());
            if(i!=permissions.size()-1){
                stringBuilder.append(",");
            }
        }
        ThreadLocalData.setThreadLocal(new Pair<>("permissons",stringBuilder.toString()));
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加用户角色
        simpleAuthorizationInfo.addRole(role);
        // 添加用户权限
        permissions.stream().forEach(t->simpleAuthorizationInfo.addStringPermission(t.getPermission()));
        return simpleAuthorizationInfo;
    }
}
