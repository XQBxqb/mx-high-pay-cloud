package cn.high.mx.framework.hadoop.core;

import java.io.InputStream;

public interface HadoopService {
    public void uploadFile(InputStream inputStream,String fileName);

    public InputStream downloadFile(String fileName);

    public void deleteFile(String fileName);
}
