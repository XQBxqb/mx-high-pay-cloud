package cn.high.mx.module.mission.exception;

import cn.high.mx.module.mission.exception.enums.SysStatusEnum;

public class SysException extends BaseException {
    public SysException(SysStatusEnum statusEnum) {
        super(statusEnum);
    }
}
