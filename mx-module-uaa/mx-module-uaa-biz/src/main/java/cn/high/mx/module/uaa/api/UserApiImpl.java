package cn.high.mx.module.uaa.api;

import cn.high.mx.module.uaa.api.dto.DemoDto;
import cn.high.mx.module.uaa.service.UaaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiImpl implements UserApi{
    private final UaaService uaaService;
    @Override
    public DemoDto demo() {
        return new DemoDto(200,"hi feign");
    }
}
