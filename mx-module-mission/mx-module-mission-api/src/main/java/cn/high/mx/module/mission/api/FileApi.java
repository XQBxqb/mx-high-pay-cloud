package cn.high.mx.module.mission.api;

import cn.high.mx.module.mission.config.FeignInterceptor;
import cn.high.mx.module.mission.res.RestRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(FileApi.PREFIX)
@FeignClient(name = FileApi.ClientName,configuration = FeignInterceptor.class)
public interface FileApi {
    public static final String ClientName = "cloud-mission";

    public static final String PREFIX = "rpc/mission/file";


    // 上传图片
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    RestRes<Object> uploadPic(@RequestPart("file") MultipartFile file, @RequestParam("type") Integer type);

    // 删除图片
    @DeleteMapping("/delete")
    RestRes<Object> deletePic(@RequestParam("key") String key);

    // 批量删除图片
    @DeleteMapping("/upload/deletes")
    RestRes<Object> deletesPic(@RequestParam("keys") String[] keys);
}
