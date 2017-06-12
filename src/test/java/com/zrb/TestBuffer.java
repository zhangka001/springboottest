package com.zrb;

import org.junit.Test;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.test.context.TestExecutionListeners;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区（Buffer）在Java NIO中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 * 根据数据类型不同（Boolean 除外） ，提供了相应的类型的缓冲区
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * LongBuffer
 * DoubleBuffer
 * IntBuffer
 * FloatBuffer
 *
 * 以上缓存区的管理方式几乎一致，通过 allocate() 获取缓存区‘
 *
 * 五、直接缓冲区和非直接缓冲区
 * 非直接缓冲区 ： 通过allocation() 方法分配缓冲区，将缓冲区建立在JVM的内存中
 * 直接缓冲区： 通过 allocationDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class TestBuffer {

    @Test
    public void test3(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }
    @Test
    public void test2(){
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst,0,2);
        System.out.println(buf.position());
        buf.mark();
        buf.get(dst,2,2);
        System.out.println(buf.position());
        buf.reset();//恢复到mark的位置
        System.out.println(buf.position());
        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){
            //获取缓冲区中可以操作的数据个数
            System.out.println(buf.remaining());
        }

    }

    @Test
    public void test1(){
        String str = "abcde";
        //1.分配一个指定大小的缓存区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("------------------allocation()---------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        buf.put(str.getBytes());
        System.out.println("---------------put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //切换读取数据模式
        buf.flip();
        System.out.println("------------------flip()--------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());


        //利用get()读取缓存区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst,0, dst.length));
        System.out.println("-----------get()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5. rewind()：可重复读,将position的位置至为0

        buf.rewind();
        System.out.println("-----------rewind()-------------");
        System.out.println(buf.position()); //0
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6. clear() 清空缓冲区，但是缓冲区中的数据依然存在，但是处于"被遗忘状态"
        buf.clear();

        System.out.println("-----------clear()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        //状态全部复位

        System.out.println((char) buf.get());
    }
}






















