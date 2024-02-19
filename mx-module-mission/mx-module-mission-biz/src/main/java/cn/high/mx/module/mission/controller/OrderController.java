package cn.high.mx.module.mission.controller;


import cn.high.mx.module.mission.dto.OrderDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "订单管理")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    @ApiOperation(value = "获取订单列表")
    @GetMapping
    public RestRes<List<OrderDetailDTO>> getOrderList(HttpServletRequest request) {
        return orderService.getOrderTOList(request);
    }
}
