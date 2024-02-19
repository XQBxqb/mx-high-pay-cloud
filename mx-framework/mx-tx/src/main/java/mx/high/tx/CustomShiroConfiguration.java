package mx.high.tx;

import cn.high.mx.framework.security.core.ShiroConfiguration;
import cn.high.mx.framework.security.core.jwt.JwtRealm;
import cn.high.mx.framework.security.core.ShiroFilter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomShiroConfiguration implements ShiroConfiguration {

    @Override
    public ShiroFilter getShiroFilter() {
        log.info("init TXshiroRouting");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dome","test");
        return new CustomShiroFilter(new HashMap<String, Filter>(),hashMap);
    }

    @Override
    public JwtRealm getJwtRealm() {
        return new CustomJwtRealm();
    }

    private class CustomJwtRealm extends JwtRealm {
        @Override
        public Boolean matchToken(Long id, String jwt) {
            return true;
        }
    }
    private class CustomShiroFilter extends ShiroFilter.AbstractShiroFilter {
        public CustomShiroFilter(Map<String, Filter> filterMap, Map<String, String> filterRuleMap) {
            super(filterMap, filterRuleMap);
        }
    }
}
