package cn.high.mx.module.mission.service;

import cn.high.mx.module.mission.api.dto.WelcomeVO;
import cn.high.mx.module.mission.res.RestRes;

public interface HomeService {
    public RestRes<WelcomeVO> welcome();
}
