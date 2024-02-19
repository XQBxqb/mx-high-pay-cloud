package cn.high.mx.framework.security.core;

import javax.servlet.Filter;
import java.util.Map;

public interface ShiroFilter {
    public Map<String, Filter> getFilterMap();

    public Map<String, String> getFilterRuleMap();

    public static abstract class AbstractShiroFilter implements ShiroFilter {
        private final Map<String, Filter> filterMap;

        private final Map<String,String> filterRuleMap;

        public AbstractShiroFilter(Map<String, Filter> filterMap, Map<String, String> filterRuleMap) {
            this.filterMap = filterMap;
            this.filterRuleMap = filterRuleMap;
        }

        public Map<String, Filter> getFilterMap() {
            return filterMap;
        }

        public Map<String, String> getFilterRuleMap() {
            return filterRuleMap;
        }
    }
}
