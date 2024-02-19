package cn.high.mx.framework.common.exception;

import cn.high.mx.framework.common.exception.enums.SysStatusEnum;

public class SysException extends BaseException{
    public SysException(SysStatusEnum  statusEnum) {
        super(statusEnum);
    }
}
