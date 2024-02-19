package cn.high.mx.module.uaa.service;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.uaa.dto.ReqLoginDto;
import cn.high.mx.module.uaa.dto.ReqRegisterDto;
import cn.high.mx.module.uaa.dto.ReqUpdateDto;
import cn.high.mx.module.uaa.dto.ResUserCommonDto;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface UaaService {
    public RestRes<String> login(ReqLoginDto reqLoginDto);

    public RestRes<ResUserCommonDto> register(ReqRegisterDto reqRegisterDto);

    public RestRes<ResUserCommonDto> updatePass(ReqUpdateDto reqUpdateDto, HttpServletRequest request);

    public RestRes<ResUserCommonDto> logout(HttpServletRequest request);
}
