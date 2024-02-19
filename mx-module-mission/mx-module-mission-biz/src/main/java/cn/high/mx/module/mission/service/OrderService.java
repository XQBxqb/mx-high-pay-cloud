package cn.high.mx.module.mission.service;


import cn.high.mx.module.mission.api.dto.OrderDTO;
import cn.high.mx.module.mission.dto.OrderDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    public RestRes<PageInfo<OrderDTO>> findOrders(Integer page, Integer pageSize, Long id);

    public RestRes<List<OrderDetailDTO>> getOrderTOList(HttpServletRequest httpServletRequest);
}
