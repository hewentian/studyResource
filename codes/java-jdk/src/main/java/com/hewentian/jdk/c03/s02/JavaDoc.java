package com.hewentian.jdk.c03.s02;

/**
 * javadoc 演示程序--<b>javadoc<b/>
 * 用法：在DOS窗口中用下面的命令编译产生DOC文档
 * javadoc -d ./myJavaDoc JavaDoc.java
 * 将在当前目录下的myJavaDoc文件夹中产生JavaDoc.java代码的文档
 *
 * @author Tim Ho
 * @version 1.0 2012/05/24
 */
public class JavaDoc {
    /**
     * 在main()方法中使用的显示用字符串
     *
     * @see #main(java.lang.String[])
     */
    static String sDisplay;
    static String 变量;

    /**
     * 显示JavaDoc
     *
     * @param args 从命令行中带入的字符串
     * @return 无
     */
    public static void main(String args[]) {
        sDisplay = "Hello World!";
        变量 = "test";
        System.out.println(sDisplay + 变量);
    }
}
