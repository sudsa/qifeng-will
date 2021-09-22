package com.hanxiaozhang.io;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述: <br>
 * 〈OSFileIO〉
 *
 * @Author:hanxinghua
 * @Date: 2021/8/14
 */
public class OSFileIO {

    static byte[] data = "123456789\n".getBytes();
    static String path = "C:\\Users\\han\\Desktop\\out.txt";


    public static void main(String[] args) throws Exception {

        String arg = "3";

        switch (arg) {
            case "0":
                basicFileIOWrite();
                break;
            case "1":
                bufferedFileIOWrite();
                break;
            case "2":
                byteBuffer();
            case "3":
                randomAccessFileWriteAndFileChannel();
            default:

        }
    }



    /**
     * 最基本的file写
     *
     * @throws Exception
     */
    public static void basicFileIOWrite() throws Exception {
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        while (true) {
            Thread.sleep(10);
            out.write(data);
        }
    }


    /**
     * 使用buffer的file写
     *  jvm  8kB   syscall  write(8KBbyte[])
     *
     * @throws Exception
     */
    public static void bufferedFileIOWrite() throws Exception {
        File file = new File(path);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            Thread.sleep(10);
            out.write(data);
        }
    }


    /**
     * NIO
     * ByteBuffer的简单使用
     */
    public static void  byteBuffer() {

        // 方式一：使用直接内存，堆外内存，即JVM的堆外
        // ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 方式二：使用直接内存，堆外内存，即JVM的堆外
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);


        System.out.println("此缓冲区的位置: " + buffer.position());
        System.out.println("缓冲区的限制: " + buffer.limit());
        System.out.println("缓冲区的容量: " + buffer.capacity());
        System.out.println("缓存区的标记: " + buffer);

        buffer.put("123".getBytes());
        System.out.println("-------------写入:123");
        System.out.println("缓存区的标记: " + buffer);

        buffer.flip();
        System.out.println("-------------读写交替");
        System.out.println("缓存区的标记:" + buffer);

        buffer.get();
        System.out.println("-------------获取");
        System.out.println("缓存区的标记:" + buffer);

        // 只想把Buffer的一部分输出，然后下次再接着操作，下次操作的时候下标也要从0开始，
        // 这样做毫无疑问是低效率的，可以compact()，改变position的位置
        buffer.compact();
        System.out.println("-------------compact......");
        System.out.println("缓存区的标记: " + buffer);

        buffer.clear();
        System.out.println("-------------清除......");
        System.out.println("缓存区的标记:" + buffer);

    }


    /**
     *  RandomAccessFile是文件NIO
     *  RandomAccessFile既可以读取文件内容，也可以向文件输出数据。
     *  同时，RandomAccessFile支持“随机访问”的方式，程序快可以直接跳转到文件的任意地方来读写数据。
     *
     *  使用场景：
     *  i. 由于RandomAccessFile可以自由访问文件的任意位置，所以如果需要访问文件的部分内容，
     *  而不是把文件从头读到尾，使用RandomAccessFile将是更好的选择。
     *  ii. 如果程序需要向已存在的文件后追加内容，则应该使用RandomAccessFile。
     *  iii. 网络请求中的多线程下载及断点续传。
     *
     * @throws Exception
     */
    public static void randomAccessFileWriteAndFileChannel() throws Exception {

        //  mode参数可选：  r： 以只读方式打开 、rw：打开以便读取和写入
        //  rws：打开以便读取和写入，要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备
        //  rwd：打开以便读取和写入，要求对“文件的内容”的每个更新都同步写入到基础存储设备
        RandomAccessFile raf = new RandomAccessFile(path, "rw");

        // 普通写
        raf.write("hello xiaozhou\n".getBytes());
        raf.write("hello xiaohan\n".getBytes());
        // 可以打一个断点
        System.out.println("-------------普通写");

        // 随机写
        // 设置文件指针偏移量，设置为4
        raf.seek(4);
        raf.write("1111".getBytes());
        // 可以打一个断点
        System.out.println("-------------随机写");

        // 堆外映射写
        // FileChannel是一个用读写，映射和操作一个文件的通道。
        // 对于文件的复制，平时我们都是使用输入输出流进行操作，利用源文件创建出一个输入流，然后利用目标文件创建出一个输出流，
        // 最后将输入流的数据读取写入到输出流中。这样也是可以进行操作的。但是利用fileChannel是很有用的一个方式。
        // 它能直接连接输入输出流的文件通道，将数据直接写入到目标文件中去。而且效率更高。
        FileChannel rafChannel = raf.getChannel();
        // mmap(内核系统调用) 系统堆外（内存中独立空间），并且与文件映射
        MappedByteBuffer map = rafChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);

        // 不是系统调用 ，但是数据会到达内核的pageCache
        //  曾经我们是需要out.write()这样的系统调用，才能让程序的数据进入内核的pageCache，即必须有用户态内核态切换
        //  mmap的内存映射，依然是内核的pageCache体系所约束的！！！ 即，换言之，丢数据。
        //  你可以去github上找一些其他C程序员写的jni扩展库，使用linux内核的Direct IO ，直接IO是忽略linux的pageCache，
        //  是把pageCache交给了程序自己开辟一个字节数组当作pageCache，动用代码逻辑来维护一致性/dirty等等一系列复杂问题
        map.put("@@@".getBytes());
        // 可以打一个断点
        System.out.println("-------------堆外映射写");

        //  map.force == flush
//      map.force();

        // 使用ByteBuffer
        raf.seek(0);
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        //  rafChannel.read(buffer) 约等于 buffer.put() ，从此通道读取字节序列到给定缓冲区
        int size = rafChannel.read(buffer);
        System.out.println("读取大小： "+size);
        System.out.println(buffer);
        // 读写交替
        buffer.flip();
        System.out.println(buffer);
        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print(((char) buffer.get(i)));
        }

    }


}
