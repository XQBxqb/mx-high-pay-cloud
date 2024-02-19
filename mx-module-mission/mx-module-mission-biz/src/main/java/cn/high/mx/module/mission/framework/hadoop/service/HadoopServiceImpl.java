package cn.high.mx.module.mission.framework.hadoop.service;

import cn.high.mx.framework.hadoop.config.HadoopProperties;
import cn.high.mx.framework.hadoop.core.HadoopService;
import cn.high.mx.module.mission.exception.SysException;
import cn.high.mx.module.mission.exception.enums.SysStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class HadoopServiceImpl implements HadoopService {

    private final HadoopProperties hadoopProperties;

    private final FileSystem fileSystem;


    @Override
    @SneakyThrows
    public void uploadFile(InputStream inputStream,String fileName) {
        String node = hadoopProperties.getNode();
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(node + fileName));
        int len;
        byte[] bytes = new byte[1024];
        while((len = inputStream.read(bytes))!=-1){
            fsDataOutputStream.write(bytes,0,len);
        }
        inputStream.close();
        fsDataOutputStream.close();
    }

    @Override
    @SneakyThrows
    public InputStream downloadFile(String fileName) {
        String node = hadoopProperties.getNode();
        return fileSystem.open(new Path(node + fileName));
    }

    @Override
    @SneakyThrows
    public void deleteFile(String fileName) {
        boolean res = fileSystem.delete(new Path(hadoopProperties.getNode() + fileName), false);
        if(!res)
            throw new SysException(SysStatusEnum.RES_FILE_DELETE_ERROR);
    }
}
