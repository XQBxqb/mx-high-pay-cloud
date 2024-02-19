package cn.gene.unit;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 昴星
 * @date 2023-10-08 16:54
 * @explain
 */
public class UnitTest2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);

        OutputStream outputStream = socket.getOutputStream();
        String s = new String("hello.world");
        outputStream.write(s.getBytes());
        outputStream.close();
    }
}
