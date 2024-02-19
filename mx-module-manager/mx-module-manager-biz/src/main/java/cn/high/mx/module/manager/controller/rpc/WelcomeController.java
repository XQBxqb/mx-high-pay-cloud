package cn.high.mx.module.manager.controller.rpc;

import cn.high.mx.module.mission.api.HomeApi;
import cn.high.mx.module.mission.api.dto.WelcomeVO;
import cn.high.mx.module.mission.res.RestRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "首页信息")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/welcome")
public class WelcomeController {
    private final HomeApi homeApi;

    @ApiOperation(value = "获取商品信息")
    @GetMapping
    @RequiresPermissions("INDEX_ADMIN_USER")
    public RestRes<WelcomeVO> welcome() {
        return homeApi.welcome();
    }
}
