package cn.high.mx.module.mission.service;

import cn.high.mx.module.mission.api.dto.SeckillGoodsDTO;
import cn.high.mx.module.mission.res.RestRes;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

public interface SeckillService {
    public RestRes<String> getPath(long goodId, HttpServletRequest request);

    public RestRes<Integer> doSeckill(long goodId,String path,HttpServletRequest request);

    public RestRes<Long> doResult(long goodId,HttpServletRequest request);

    public RestRes<PageInfo<SeckillGoodsDTO>> getPage(Integer page, Integer pageSize, Long goodsId);
}
