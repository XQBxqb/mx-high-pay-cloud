package cn.high.mx.module.manager.controller.rpc;

import cn.high.mx.module.mission.api.OrderApi;
import cn.high.mx.module.mission.api.dto.OrderDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderApi orderApi;
    @ApiOperation(value = "分页查询订单")
    @GetMapping
    @RequiresPermissions("ORDER_ADMIN_USER")
    public RestRes<PageInfo<OrderDTO>> findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long id) {
        return orderApi.findByOrders(page, pageSize, id);
    }
}
