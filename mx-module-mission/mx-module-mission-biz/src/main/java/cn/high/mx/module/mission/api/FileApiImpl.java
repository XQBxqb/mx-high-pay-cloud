package cn.high.mx.module.mission.api;

import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileApiImpl implements FileApi{
    private final FileService fileService;
    @Override
    public RestRes<Object> uploadPic(MultipartFile file, Integer type) {
        return fileService.uploadPic(file,type);
    }

    @Override
    public RestRes<Object> deletePic(String key) {
        return fileService.deletePic(key);
    }

    @Override
    public RestRes<Object> deletesPic(String[] keys) {
        return fileService.deletesPic(keys);
    }
}
