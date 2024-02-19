package cn.high.mx.module.mission.service.impl;

import cn.high.mx.framework.hadoop.core.HadoopService;
import cn.high.mx.module.mission.api.consts.FileConst;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.FileService;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final HadoopService hadoopService;


    private final Redisson redisson;

    @Override
    @SneakyThrows
    public RestRes<Object> uploadPic(MultipartFile file, Integer type) {
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        String suffix = filename.substring(index, filename.length());
        RAtomicLong atomicLong = redisson.getAtomicLong(FileConst.FILE_SIGN);
        long sign = atomicLong.addAndGet(1);
        String filePathName = FileConst.PATH_PIC + formatDate() + sign + suffix;
        hadoopService.uploadFile(file.getInputStream(), filePathName);
        return RestRes.ok(filePathName);
    }

    @Override
    public RestRes<Object> deletePic(String key) {
        hadoopService.deleteFile(key);
        return RestRes.ok();
    }

    @Override
    public RestRes<Object> deletesPic(String[] keys) {
        Arrays.stream(keys).forEach(hadoopService::deleteFile);
        return RestRes.ok();
    }

    private String formatDate() {
        return DateUtil.formatDate(new Date());
    }

}
