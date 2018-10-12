package com.jesper.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());//表示 ByteBuf 底层占用了多少字节的内存（包括丢弃的字节、可读字节、可写字节）
        System.out.println("maxCapacity(): " + buffer.maxCapacity());//表示 ByteBuf 底层最大能够占用多少字节的内存，当向 ByteBuf 中写数据的时候，如果发现容量不足，则进行扩容，直到扩容到 maxCapacity，超过这个数，就抛异常
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());//readableBytes() 表示 ByteBuf 当前可读的字节数，它的值等于 writerIndex-readerIndex
        System.out.println("isReadable(): " + buffer.isReadable());// writerIndex-readerIndex，如果两者相等，则不可读，isReadable() 方法返回 false
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());//writableBytes() 表示 ByteBuf 当前可写的字节数，它的值等于 capacity-writerIndex
        System.out.println("isWritable(): " + buffer.isWritable());// capacity-writerIndex，如果两者相等，则表示不可写，isWritable() 返回 false
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());//maxWritableBytes() 就表示可写的最大字节数，它的值等于 maxCapacity-writerIndex
        System.out.println();
    }
}
