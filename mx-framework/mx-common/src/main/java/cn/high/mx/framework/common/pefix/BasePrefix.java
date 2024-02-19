package cn.high.mx.framework.common.pefix;

public class BasePrefix implements Prefix{
    private final String prefixValue;

    public BasePrefix(String prefixValue) {
        this.prefixValue = prefixValue;
    }

    @Override
    public String getPrefixValue() {
        return this.getClass().getName()+this.prefixValue;
    }
}
