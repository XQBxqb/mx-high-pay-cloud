package cn.high.mx.module.manager.controller.rpc;

import cn.high.mx.module.mission.api.SeckillApi;
import cn.high.mx.module.mission.api.dto.SeckillGoodsDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "秒杀管理")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/seckill")
public class SeckillGoodsController {
    private final SeckillApi seckillApi;
    @ApiOperation(value = "分页获取秒杀商品")
    @GetMapping
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<PageInfo<SeckillGoodsDTO>> seckillIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long goodsId) {
        return seckillApi.seckillIndex(page, pageSize, goodsId);
    }
}
