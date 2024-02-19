package cn.high.mx.module.uaa.controller;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.uaa.dto.ReqLoginDto;
import cn.high.mx.module.uaa.dto.ReqRegisterDto;
import cn.high.mx.module.uaa.dto.ReqUpdateDto;
import cn.high.mx.module.uaa.dto.ResUserCommonDto;
import cn.high.mx.module.uaa.service.UaaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(UserController.UAA_ROUTING)
@Validated
public class UserController {

    private final UaaService uaaService;
    public static final String UAA_ROUTING = "user";

    @ApiOperation(value = "登录")
    @PostMapping("/doLogin")
    public RestRes<String> doLogin(@Valid @RequestBody ReqLoginDto reqLoginDto) {
        return uaaService.login(reqLoginDto);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/doRegister")
    public RestRes<ResUserCommonDto> doRegister(@Valid @RequestBody ReqRegisterDto reqRegisterDto) {
        return uaaService.register(reqRegisterDto);
    }

    @ApiOperation(value = "更新密码")
    @PostMapping("/updatePass")
    public RestRes<ResUserCommonDto> updatePass(@Valid @RequestBody ReqUpdateDto reqUpdateDto, HttpServletRequest request) {
        return uaaService.updatePass(reqUpdateDto, request);
    }

    @ApiOperation(value = "登出")
    @GetMapping("/doLogout")
    public RestRes<ResUserCommonDto> doLogout(HttpServletRequest request) {
        return uaaService.logout(request);
    }
}
