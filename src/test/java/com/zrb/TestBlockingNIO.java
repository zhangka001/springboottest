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
 * 使用NIO完成网络通信的三个核心
 * 1. Channel reposiable for connection
 *      java.nio.channels.Channel
 *          |--SelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 *
 *              |--Pipe.SinkChannel
 *              |--Pipe.SourceChannel
 *
 * 2. Buffer : store data
 * 3. Selector : 是SelectableChannel的 多路复用器。用于监控SelectableChannel的IO状况
 */
public class TestBlockingNIO {
    @Test
    public void client() throws Exception{
        //1. Get Channel
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        //2. Allocate specified size of buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //3. Read local files and send server
        while (inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4. close the Channels
        inChannel.close();
        sChannel.close();
    }


    @Test
    public void server() throws Exception{
        //1. Get Channel
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("10.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        //2. bind the connection
        ssChannel.bind(new InetSocketAddress(9898));

        //3.Get client connection Channel
        SocketChannel sChannel = ssChannel.accept();

        // 4.Allocate specified size of buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 5. Get client data,and save data to local
        while (sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        ssChannel.close();
        outChannel.close();
    }
}


















