package cn.high.mx.module.manager.framework.shiro.core.jwt;



import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;


@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        BaseAdminUser adminUser = (BaseAdminUser)authenticationInfo.getPrincipals()
                                                                   .getPrimaryPrincipal();
        log.info("user {} finish token login",adminUser);
        return true;
    }
}