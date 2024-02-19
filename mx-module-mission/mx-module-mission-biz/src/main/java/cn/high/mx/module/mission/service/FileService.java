package cn.high.mx.module.mission.service;

import cn.high.mx.module.mission.res.RestRes;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public RestRes<Object> uploadPic(MultipartFile file, Integer type);

    public RestRes<Object> deletePic(String key) ;

    public RestRes<Object> deletesPic(String[] keys);
}
