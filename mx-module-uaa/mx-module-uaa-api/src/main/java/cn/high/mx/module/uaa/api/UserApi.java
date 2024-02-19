package cn.high.mx.module.uaa.api;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.uaa.api.dto.DemoDto;
import cn.high.mx.module.uaa.config.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = UserApi.ClientName,configuration = FeignInterceptor.class)
public interface UserApi {
    static String ClientName = "cloud-uaa";
    static String PREFIX = "/rpc/uaa";

    @GetMapping(PREFIX + "/demo")
    public DemoDto demo();
}
