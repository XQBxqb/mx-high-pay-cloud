package cn.high.mx.module.manager.controller.rpc;

import cn.high.mx.module.mission.api.GoodApi;
import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(tags = "商品管理")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/goods")
public class GoodsController {
    private final GoodApi goodApi;

    @ApiOperation(value = "分页条件查询商品")
    @GetMapping
    @RequiresPermissions("GOODS_ADMIN_USER")
    public RestRes<PageInfo<GoodsDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                  @RequestParam(required = false) String goodsName) {
        return goodApi.goodsIndex(page, pageSize, goodsName);
    }

    @ApiOperation(value = "创建商品")
    @PostMapping
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<Object> goodsCreate(@RequestBody @Valid GoodsDTO goodsDTO) {
        return goodApi.goodsCreate(goodsDTO);
    }

    @ApiOperation(value = "更新商品")
    @PutMapping
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<Object> goodsUpdate(@RequestBody @Valid GoodsDTO goodsDTO) {
        return goodApi.goodsUpdate(goodsDTO);
    }

    @ApiOperation(value = "获取商品信息")
    @GetMapping("/{id}/edit")
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<GoodsDTO> goodsEdit(@PathVariable("id") Long id) {
        return goodApi.goodsEdit(id);
    }

    @ApiOperation(value = "更改商品是否启用")
    @GetMapping("/updateUsing/{id}")
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<Object> goodsUsing(@PathVariable("id") Long id) {
        return goodApi.goodsUsing(id);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/{id}")
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<Object> goodsDelete(@PathVariable("id") Long id) {
        return goodApi.goodsDelete(id);
    }

    @ApiOperation(value = "批量删除商品")
    @DeleteMapping("/deletes")
    @RequiresPermissions("SECKILL_ADMIN_USER")
    public RestRes<Object> goodsDeletes(@RequestParam String ids) {
        return goodApi.goodsDeletes(ids);
    }
}
