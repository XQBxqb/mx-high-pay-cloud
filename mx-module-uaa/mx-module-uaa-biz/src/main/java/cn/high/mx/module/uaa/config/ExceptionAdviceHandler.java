package cn.high.mx.module.uaa.config;

import cn.high.mx.framework.common.exception.BizException;
import cn.high.mx.framework.common.exception.SysException;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {
    @ExceptionHandler(SysException.class)
    public RestRes<Void> handSysException(SysException sysException){
        log.error("system error:{} msg:{} ",sysException,sysException.getMessage());
        return RestRes.errorEnum(sysException.getBaseStatusEnum());
    }
    @ExceptionHandler(BizException.class)
    public RestRes<Void> handBizException(BizException bizException){
        log.error("biz error:{} msg:{} ",bizException,bizException.getMessage());
        return RestRes.errorEnum(bizException.getBaseStatusEnum());
    }
    @ExceptionHandler(RuntimeException.class)
    public RestRes<Void> handlerRuntimeException(RuntimeException runtimeException){
        log.error("biz error:{} msg:{}",runtimeException,runtimeException.getMessage());
        return RestRes.errorEnum(BizStatusEnum.RES_RUNTIME_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public RestRes<Void> handlerConstraintViolationException(ConstraintViolationException constraintViolationException){
        StringBuilder errors = new StringBuilder();
        constraintViolationException.getConstraintViolations().stream().forEach(error-> errors.append(error.getPropertyPath().toString()+"-"+error.getMessage()+";"));
        log.error("biz error param incorrect "+errors.toString(),constraintViolationException);
        return RestRes.errorEnum(BizStatusEnum.RES_PARAM_INCORRECT);
    }
}
