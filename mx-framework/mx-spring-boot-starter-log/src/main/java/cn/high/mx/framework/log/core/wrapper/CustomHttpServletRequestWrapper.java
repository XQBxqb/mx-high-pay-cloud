package cn.high.mx.framework.log.core.wrapper;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    private Map<String, String[]> parameterMap;

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        parameterMap = request.getParameterMap();
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(body==null){
            ServletInputStream inputStream = super.getInputStream();
            byte[] bytes=new byte[1024];
            int len;
            StringBuilder stringBuilder = new StringBuilder();
            while((len=inputStream.read(bytes))!=-1){
                stringBuilder.append(new String(bytes,0,len));
            }
            body = stringBuilder.toString().trim().length()==0 ? new byte[0]:stringBuilder.toString().getBytes();
        }
        return new ServletInputStream() {
            ByteArrayInputStream byteArray=new ByteArrayInputStream(body);
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArray.read();
            }
        };
    }
}
