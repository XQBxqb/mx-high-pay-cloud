package cn.high.mx.framework.security.core.jwt;

import cn.high.mx.framework.common.exception.BizException;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.security.core.consts.JwtConsts;
import cn.high.mx.framework.security.core.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public abstract class JwtRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        //获得token->id
        Long id = JwtUtils.parseToken(jwt, JwtConsts.JWT_TOKEN_KEY);
        Boolean matchToken = matchToken(id, jwt);
        if(matchToken) return new SimpleAuthenticationInfo(jwt,jwt,"JwtRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
        throw new BizException(BizStatusEnum.RES_TOKEN_ERROR);
    }

    public abstract Boolean matchToken(Long id,String jwt);
}
