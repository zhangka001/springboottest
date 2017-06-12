package com.zrb;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * 1. Channel is used connected between destination node and source node.
 * Responsible for transmission of Buffer Region.
 * Channel don't save data , so it needs Buffer-Region to save data.
 * 2. The majar of implement classes
 * java.nio.channels.Channel
 *          |--FileChannel  use local
 *          |--SocketChannel use TCP/IP
 *          |--ServerSocketChannel use TCP/IP
 *          |--DatagramChannel use UDP
 *
 * 3. Get Channel
 *  Java provide method named "getChannel()" to get the Channel
 *  1> local IO
 *          FileInputStream/FileOutputStream/RandomAccessFile
 *  2> network IO
 *          Socket / ServerSocket / DatagramSocket
 *  3> It has static method "open()" to get the own Channel in INO.2 after JDK 1.7
 *  4> File Utils class has method of "newByteChannel()" to get Channel.
 *
 *  4. tranfer data between Channels
 *  transferFrom()/transferTo
 *
 *  5. Scatter and Gather
 *  Scatter Reads : Scatter Channel data to several buffers
 *  Gather Writes : Gather several buffer to one Channel
 *
 */
public class TestChannel {



    @Test
    public void test5(){
       Map<String,Charset> map = Charset.availableCharsets();
       Set<Map.Entry<String,Charset>> set =  map.entrySet();
        for (Map.Entry<String,Charset> entry :set){
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }


    @Test
    public void test4() throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile("1.txt","rw");

        //1. Get Channel
        FileChannel channel1 = raf1.getChannel();

        //2. Allocate specified size of buffer
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        //Scatter Reads
        ByteBuffer[] bufs = {buf1,buf2};
        channel1.read(bufs);

        for(ByteBuffer byteBuffer : bufs){
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("-------------------");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));


        //Gather Write
        RandomAccessFile raf2 = new RandomAccessFile("2.txt","rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }




    @Test
    public void test3() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("9.jpg"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        inChannel.transferTo(0,inChannel.size(),outChannel);
        //outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    //isDirectBuffer
    @Test
    public void test2() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        //Files of memory map
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //Operate the memory data by read and write directly.
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);
    }



    //not isDirectBuffer
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {

            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");
            //1. Get Channel
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            //2. Allocate specified size of Buffer
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //3. Put data of Channel in Buffer
            while (inChannel.read(buf) != -1){
                buf.flip(); //change Read-Model
                //Get inChannel data and put it in outChannel
                outChannel.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {

                }
            }

            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
