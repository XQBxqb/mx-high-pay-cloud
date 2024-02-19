package cn.high.mx.module.mission.api;


import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.impl.GoodServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GoodApiImpl implements GoodApi{
    private final GoodServiceImpl goodService;
    @Override
    public RestRes<Object> goodsCreate(GoodsDTO goodsDTO) {
        return goodService.addGood(goodsDTO);
    }

    @Override
    public RestRes<PageInfo<GoodsDTO>> goodsIndex(Integer page, Integer pageSize, String goodsName) {
        return goodService.getGoodPage(page,pageSize,goodsName);
    }

    @Override
    public RestRes<Object> goodsUpdate(GoodsDTO goodsDTO)  {
        return goodService.updateOne(goodsDTO);
    }

    @Override
    public RestRes<GoodsDTO> goodsEdit(Long id) {
        return goodService.goodsEdit(id);
    }

    @Override
    public RestRes<Object> goodsUsing(Long id) {
        return goodService.goodsUsing(id);
    }

    @Override
    public RestRes<Object> goodsDelete(Long id) {
        return goodService.goodsDelete(id);
    }

    @Override
    public RestRes<Object> goodsDeletes(String ids) {
        return goodService.goodsDeletes(ids);
    }
}
