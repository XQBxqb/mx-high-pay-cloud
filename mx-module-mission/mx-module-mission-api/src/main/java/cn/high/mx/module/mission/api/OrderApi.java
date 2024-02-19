package cn.high.mx.module.mission.api;


import cn.high.mx.module.mission.api.dto.OrderDTO;
import cn.high.mx.module.mission.config.FeignInterceptor;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = OrderApi.ClientName,configuration = FeignInterceptor.class)
@RequestMapping(OrderApi.PREFIX)
public interface OrderApi {
    public static final String ClientName = "cloud-mission";
    public static final String PREFIX = "rpc/mission/order";

    @GetMapping("/order/findByOrders")
    public RestRes<PageInfo<OrderDTO>> findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long id);
}
