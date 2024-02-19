package cn.high.mx.module.mission.controller;

import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Api(tags = "秒杀管理")
@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping(SeckillController.ROUTING_SECKILL)
public class SeckillController {
    public static final String ROUTING_SECKILL="seckill";

    private final SeckillService seckillService;
    @ApiOperation(value = "获取用户商品路径")
    @GetMapping("/getPath")
    public RestRes<String> getSeckillPath(@RequestParam("goodsId") long goodsId, HttpServletRequest request){
        return seckillService.getPath(goodsId,request);
    }
    @ApiOperation(value = "进行秒杀")
    @GetMapping
    public RestRes<Integer> doSeckill(@RequestParam("goodsId") long goodsId,
                                      @RequestParam("path") String path, HttpServletRequest request){
        return seckillService.doSeckill(goodsId,path,request);
    }
    @ApiOperation(value = "秒杀结果")
    @GetMapping("/result")
    public RestRes<Long> seckillResult(@RequestParam("goodsId") long goodsId,HttpServletRequest request){
        return seckillService.doResult(goodsId,request);
    }
}
