package cn.high.mx.framework.security.core;

import cn.high.mx.framework.security.core.jwt.JwtRealm;

public interface ShiroConfiguration {
    public ShiroFilter getShiroFilter();

    public JwtRealm getJwtRealm();
}
