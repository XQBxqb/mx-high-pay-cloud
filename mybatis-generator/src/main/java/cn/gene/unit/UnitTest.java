package cn.gene.unit;


import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 昴星
 * @date 2023-09-13 14:48
 * @explain ctrl+f12/alt+7快速看一个类的成员
 *          Ctrl+H 查看类的继承关系
 */
@Slf4j
public class UnitTest {


    public static void dateTest() {
        //该值不受时区影响
        long currentTimeMillis = System.currentTimeMillis();
        log.info("currentTimeMillis: " + currentTimeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //受时区影响,new Date()不带参数会默认时间是当前系统时间
        Date date = new Date();
        String dateTime = simpleDateFormat.format(date);
        System.out.println(dateTime);
    }

    public static void calenderTest() {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(calender.getTime());
        System.out.println(format);
    }

    public static void localDate() {
        //local to str
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDate:" + localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("localTime:" + localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("localDateTime:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.info("--------");
        //str to local
        LocalDateTime localDateTime1 = LocalDateTime.parse("2021-06-18T15:30:20Z");
        System.out.println("localDateTime1:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /*public static void main(String[] args) throws FileNotFoundException {
        File file=new File("test");
        try {
            if(!file.exists()) file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        String s = new String("adfasdfdsfsfsdfsffsafsadffffffffffffffffafggasggasgs");
        byte[] bytes = s.getBytes();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
        }
    }*/

    /*public static void main(String[] args) {
        File file=new File("test");
        InputStream inputStream;
        try {
             inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = new byte[1024];
        int len ;
        try {
            while((len = inputStream.read(bytes))!=-1){
                System.out.println(len);
                String s = new String(bytes, 0, len);
                System.out.println(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    /*public static void main(String[] args) {
        File file = new File("test");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] bytes = new byte[1024];
        int len;
        try {
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
    /*public static void main(String[] args) {
        File file = new File("test");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
        }
        while(true){
            try {
                int read = fileInputStream.read();
                if(read==-1) break;
                System.out.println(read);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

    /*public static void main(String[] args) {
        byte[] bytes = new byte[10];
        for(int i=0;i<10;i++)
        bytes[i]=(byte) 33;
        File file = new File("test3");
        if(!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*public static void main(String[] args) {
        File file = new File("test");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] bytes = new byte[1024];
        int len;

        try {
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
    public static void main(String[] args) {
        Selector selector ;
        try {
            selector = Selector.open();
            ServerSocketChannel ssChanner = ServerSocketChannel.open();
            ssChanner.configureBlocking(false);
            ssChanner.register(selector, SelectionKey.OP_ACCEPT);

            ServerSocket socket = ssChanner.socket();
            InetSocketAddress localhost = new InetSocketAddress("127.0.0.1", 8888);
            socket.bind(localhost);

            while (true){
                int select = selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()){
                        ServerSocketChannel channel =(ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = channel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    }else{
                        if(key.isReadable()){
                            SocketChannel channel = (SocketChannel)key.channel();
                            ServerReadFromClient(channel);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ServerReadFromClient(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder stringbuilder = new StringBuilder();

        while(true){
            int read = channel.read(buffer);

            if(read==-1) break;
            buffer.flip();
            int limit = buffer.limit();

            for(int i=0;i<limit;i++){
                stringbuilder.append((char)buffer.get(i));
            }

            buffer.clear();
        }

        System.out.println(stringbuilder.toString());
    }
}

