package cn.high.mx.module.mission.api;


import cn.high.mx.module.mission.api.dto.OrderDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.OrderService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderApiImpl implements OrderApi{
    private final OrderService orderService;
    @Override
    public RestRes<PageInfo<OrderDTO>> findByOrders(Integer page, Integer pageSize, Long id) {
        return orderService.findOrders(page,pageSize,id);
    }
}
