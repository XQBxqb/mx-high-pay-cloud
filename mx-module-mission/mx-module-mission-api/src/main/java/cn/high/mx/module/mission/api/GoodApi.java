package cn.high.mx.module.mission.api;


import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.config.FeignInterceptor;

import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@FeignClient(name = GoodApi.ClientName,configuration = FeignInterceptor.class)
public interface GoodApi {
    static String ClientName = "cloud-mission";
    static String PREFIX = "rpc/mission/good";

    @PostMapping(PREFIX+"/goods")
    public RestRes<Object> goodsCreate(@RequestBody @Valid GoodsDTO goodsDTO);

    @GetMapping(PREFIX+"/goods")
    public RestRes<PageInfo<GoodsDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                  @RequestParam(required = false) String goodsName) ;

    @PutMapping(PREFIX+"/goods")
    public RestRes<Object> goodsUpdate(@RequestBody @Valid GoodsDTO goodsDTO);

    @GetMapping(PREFIX+"/goods/{id}/edit")
    public RestRes<GoodsDTO> goodsEdit(@PathVariable("id") Long id) ;
    @GetMapping(PREFIX+"/goods/updateUsing/{id}")
    public RestRes<Object> goodsUsing(@PathVariable("id") Long id);
    @DeleteMapping(PREFIX+"/goods/{id}")
    public RestRes<Object> goodsDelete(@PathVariable("id") Long id);
    @DeleteMapping(PREFIX+"/goods/deletes")
    public RestRes<Object> goodsDeletes(@RequestParam String ids);

}
