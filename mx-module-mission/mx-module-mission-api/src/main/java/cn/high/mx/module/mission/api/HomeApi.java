package cn.high.mx.module.mission.api;

import cn.high.mx.module.mission.api.dto.WelcomeVO;
import cn.high.mx.module.mission.config.FeignInterceptor;
import cn.high.mx.module.mission.res.RestRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = HomeApi.ClientName,configuration = FeignInterceptor.class)
@RequestMapping(HomeApi.PREFIX)
public interface HomeApi {
    public static final String ClientName = "cloud-mission";

    public static final String PREFIX = "rpc/mission/home";

    @GetMapping("/welcome")
    public RestRes<WelcomeVO> welcome();
}
