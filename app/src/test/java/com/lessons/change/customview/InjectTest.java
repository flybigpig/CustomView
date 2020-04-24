package com.lessons.change.customview;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InjectTest {
    public static void main(String[] args) {

        try {
            /**
             * 1、准备待分析的class
             */
            FileInputStream fis = new FileInputStream
                    ("InjectTest.class");
            /**
             * 2、执行分析与插桩
             */
            //class字节码的读取与分析引擎
            ClassReader cr = new ClassReader(fis);
            // 写出器 COMPUTE_FRAMES 自动计算所有的内容，后续操作更简单
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            //分析，处理结果写入cw EXPAND_FRAMES：栈图以扩展格式进行访问
            cr.accept(new ClassAdapterVisitor(cw), ClassReader.EXPAND_FRAMES);
            /**
             * 3、获得结果并输出
             */
            byte[] newClassBytes = cw.toByteArray();
            File file = new File("..\\app\\src\\test\\java\\com\\lessons\\change\\customview");
            file.mkdirs();
            FileOutputStream fos = new FileOutputStream
                    ("InjectTest.class");
            fos.write(newClassBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
