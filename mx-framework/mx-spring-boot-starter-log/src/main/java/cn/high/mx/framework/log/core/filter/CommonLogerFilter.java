package cn.high.mx.framework.log.core.filter;


import cn.high.mx.framework.log.core.wrapper.CustomHttpServletRequestWrapper;
import cn.high.mx.framework.log.core.wrapper.CustomHttpServletResponseWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class CommonLogerFilter extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String params;
        String body;
        CustomHttpServletRequestWrapper wrappedRequest = null;
        CustomHttpServletResponseWrapper wrappedResponse = null;
        if(StringUtils.isNotBlank(request.getHeader("Content-Type"))&&request.getHeader("Content-Type").equals("multipart/form-data")){
            params = getParamsCommon(request);
            body = "body type - file";
            log.info("Request: method={}, uri={}, params={} ,payload={}",request.getMethod(),request.getRequestURI(),params,body);
            chain.doFilter(request,response);
        }else{
            wrappedRequest = new CustomHttpServletRequestWrapper(request);
            wrappedResponse = new CustomHttpServletResponseWrapper(response);
            params= getParamsCommon(wrappedRequest);
            body = inputStreamToStr(wrappedRequest.getInputStream(), wrappedRequest.getCharacterEncoding());
            log.info("Request: method={}, uri={}, params={} ,payload={}", wrappedRequest.getMethod(),
                    wrappedRequest.getRequestURI(),params ,body);
            chain.doFilter(wrappedRequest, wrappedResponse);
        }
        log.info("Response : status "+wrappedResponse.getStatus() + " ,payload = "+bytesToStr(wrappedResponse.getContentAsByteArray(), wrappedRequest.getCharacterEncoding()));
    }


    private static String getParamsCommon(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder paramsBuilder = new StringBuilder();
        parameterMap.forEach((key, values) -> {
            String valuesString = (values == null) ? "null"
                    : Arrays.stream(values)
                            .map(value -> value == null ? "null" : value)
                            .collect(Collectors.joining(", "));
            paramsBuilder.append("Parameter: " + key + ", Values: " + valuesString + ",");
        });
        return StringUtils.isBlank(paramsBuilder.toString().trim()) ? "null": paramsBuilder.toString();
    }

    @SneakyThrows
    private String inputStreamToStr(InputStream inputStream,String CODE){
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        while((len=inputStream.read(bytes))!=-1){
            stringBuilder.append(new String(bytes,0,len,CODE));
        }
        return StringUtils.isBlank(stringBuilder.toString().trim()) ? "null": stringBuilder.toString();
    }
    @SneakyThrows
    private String bytesToStr(byte[] bytes,String CODE){
        return new String(bytes,CODE);
    }

}
