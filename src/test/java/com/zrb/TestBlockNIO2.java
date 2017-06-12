package com.zrb;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Zhangrb on 2017/6/12.
 */
public class TestBlockNIO2 {
    @Test
    public void client() throws Exception{
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        ByteBuffer buff = ByteBuffer.allocate(1024);
        while (inChannel.read(buff) != -1){
            buff.flip();
            sChannel.write(buff);
            buff.clear();
        }

        sChannel.shutdownOutput();

        int len = 0;
        while ((len =sChannel.read(buff)) != -1){
            buff.flip();
            System.out.println(new String(buff.array(), 0, len));
            buff.clear();
        }

        inChannel.close();
        sChannel.close();
    }


    @Test
    public void server() throws Exception{

        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        ssChannel.bind(new InetSocketAddress(9898));
        SocketChannel schannel = ssChannel.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (schannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        buf.put("Server get data sucessfully!".getBytes());
        buf.flip();
        schannel.write(buf);

        schannel.close();
        outChannel.close();
        ssChannel.close();

    }
}

























