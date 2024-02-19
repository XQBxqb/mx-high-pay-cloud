package cn.high.mx.module.mission.controller;


import cn.high.mx.module.mission.dto.GoodsDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
    private final GoodService goodService;

    @ApiOperation(value = "获取商品列表")
    @GetMapping("/getGoodsList")
    public RestRes<List<GoodsDetailDTO>> getGoodsList() {
        return goodService.getGoods();
    }

    @ApiOperation(value = "获取商品信息")
    @GetMapping("/getDetail/{goodsId}")
    public RestRes<GoodsDetailDTO> getDetail(@PathVariable("goodsId") long goodsId) {
        return goodService.getDetail(goodsId);
    }

}
