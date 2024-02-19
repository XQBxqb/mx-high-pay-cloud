package cn.high.mx.module.mission.api;

import cn.high.mx.module.mission.api.dto.WelcomeVO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeApiImpl implements HomeApi{

    private final HomeService homeService;
    @Override
    public RestRes<WelcomeVO> welcome() {
        return homeService.welcome();
    }
}
