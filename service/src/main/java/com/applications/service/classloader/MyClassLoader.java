package com.applications.service.classloader;

import java.io.*;

/**
 * 一、类加载器类别
 *  1.java虚拟机自带的加载器
 *   根类加载器（Bootstrap，c++实现）
 *    扩展类加载器（Extension,java实现）
 *    系统类加载器（System,java实现）
 *  2.用户自定义的类加载器
 *    java.lang.ClassLoader的子类
 *    用户可以定制类的加载方式
 * 二、ClassLoader加载类的顺序
 *  1.调用 findLoadedClass(String) 来检查是否已经加载类。
 *  2.在父类加载器上调用 loadClass 方法。如果父类加载器为 null，则使用虚拟机的内置类加载器。
 *  3.调用 findClass(String) 方法查找类。
 * 三、实现自己的类加载器
 *  1.获取类的class文件的字节数组
 *  2.将字节数组转换为Class类的实例
 */
public class MyClassLoader extends ClassLoader {

    //类加载器名称
    private String name;
    //加载类的路径
    private String path = "Users/hukaisheng/selfdev/applications";
    private final String fileType = ".class";
    public MyClassLoader(String name){
        //让系统类加载器成为该 类加载器的父加载器
        super();
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent, String name){
        //显示指定该类加载器的父加载器
        super(parent);
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * 获取.class文件的字节数组
     * @param name
     * @return
     */
    private byte[] loaderClassData(String name){
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.name = this.name.replace(".", "/");
        try {
            is = new FileInputStream(new File(path + name + fileType));
            int c;
            while(-1 != (c = is.read())){
                baos.write(c);
            }
            data = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 获取Class对象
     */
    @Override
    public Class<?> findClass(String name){
        byte[] data = loaderClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //systemChild的父加载器为系统类加载器
        MyClassLoader systemChild = new MyClassLoader("systemChild");
        systemChild.setPath("User/hukaisheng/loader1");
        //custom的父加载器为systemChild
        MyClassLoader custom = new MyClassLoader(systemChild, "custom");
        custom.setPath("User/hukaisheng/loader2");
        //rootChild的父加载器为根类加载器
        MyClassLoader rootChild = new MyClassLoader(null, "rootChild");
        rootChild.setPath("User/hukaisheng/loader3");

        Class clazz = custom.loadClass("Sample");
        Object object = clazz.newInstance();
    }
}
