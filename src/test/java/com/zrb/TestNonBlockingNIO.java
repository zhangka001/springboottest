package com.zrb;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Zhangrb on 2017/6/12.
 */
public class TestNonBlockingNIO {

    @Test
    public void client(){
        SocketChannel sChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

            //change nonblocking model
            sChannel.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()){
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

//            buf.put((new Date().toString()).getBytes());
//            buf.flip();
//            sChannel.write(buf);
//            buf.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sChannel != null){
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Test
    public void server(){
        try {
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.bind(new InetSocketAddress(9898));
            Selector selector = Selector.open();
            //register the channel to selector
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
            //轮询式的获取选择器上已经“准备就绪”的事件
            while (selector.select() > 0){
                //获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    //获取准备就绪的事件
                    SelectionKey sk = it.next();
                    if(sk.isAcceptable()){
                        //如果接收就绪，获取客户端连接
                        SocketChannel sChannel = ssChannel.accept();
                        //切换非阻塞模式
                        sChannel.configureBlocking(false);
                        //将该通道注册到选择器上
                        sChannel.register(selector,SelectionKey.OP_READ);
                    }else if(sk.isReadable()){
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 10 ;
                        while ((len = sChannel.read(buf)) > 0){
                            buf.flip();
                            System.out.println(new String(buf.array(),0,len));
                            buf.clear();
                        }
                    }

                    //取消选择键 SelectionKey
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
