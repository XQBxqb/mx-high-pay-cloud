package cn.high.mx.framework.log.core.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 *  logstash 日誌輸入
 */
public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {


    private ServletOutputStream outputStream;

    private PrintWriter writer;

    private ServletOutputStreamWrapper copier;

    public CustomHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public static class ServletOutputStreamWrapper extends ServletOutputStream {
        private OutputStream outputStream;
        private ByteArrayOutputStream copy;

        public ServletOutputStreamWrapper(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.copy = new ByteArrayOutputStream();
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            copy.write(b);
        }

        public byte[] getCopy() {
            return copy.toByteArray();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }
        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            copier = new ServletOutputStreamWrapper(outputStream);
        }
        return copier;

    }


    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            copier = new ServletOutputStreamWrapper(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }
        return writer;

    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            copier.flush();
        }
    }

    public byte[] getContentAsByteArray() {
        if (copier != null) {
            return copier.getCopy();
        } else {
            return new byte[0];
        }
    }
}
