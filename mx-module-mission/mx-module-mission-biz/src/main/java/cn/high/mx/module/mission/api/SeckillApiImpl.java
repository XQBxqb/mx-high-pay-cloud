package cn.high.mx.module.mission.api;


import cn.high.mx.module.mission.api.dto.SeckillGoodsDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.SeckillService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SeckillApiImpl implements SeckillApi {
    private final SeckillService service;

    @Override
    public RestRes<PageInfo<SeckillGoodsDTO>> seckillIndex(Integer page, Integer pageSize, Long goodsId) {
        return service.getPage(page,pageSize,goodsId);
    }

}
