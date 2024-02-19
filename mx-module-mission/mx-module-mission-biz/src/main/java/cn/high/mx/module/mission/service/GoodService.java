package cn.high.mx.module.mission.service;


import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.dto.GoodsDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodService {
    public RestRes<Object> addGood(GoodsDTO goodsDTO) ;

    public RestRes<PageInfo<GoodsDTO>> getGoodPage(Integer page, Integer pageSize, String goodsName);

    public RestRes<Object> updateOne(GoodsDTO goodsDTO) ;

    public RestRes<GoodsDTO> goodsEdit(Long id);

    public RestRes<Object> goodsUsing(Long id);

    public RestRes<Object> goodsDelete(Long id) ;

    public RestRes<Object> goodsDeletes(String ids) ;

    public RestRes<List<GoodsDetailDTO>> getGoods();

    public RestRes<GoodsDetailDTO> getDetail(long goodId);
}
