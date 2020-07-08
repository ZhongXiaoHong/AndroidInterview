package com.silang.javalib;

import java.util.HashMap;
import java.util.Map;

public class SingletonTest {


    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index + "****" + Singleton3.getInstance());
                }
            }).start();
        }
    }


    //TODO 懒汉式单例，线程不安全
    static class Singleton1 {

        private static Singleton1 instance;

        private Singleton1() {
            System.out.println("懒汉单例");
        }

        //TODO 线程不安全
        public static Singleton1 getInstance() {
            if (instance == null) {
                instance = new Singleton1();
            }
            return instance;
        }
    }


    //TODO 懒汉式安全
    static class Singleton2 {

        private static Singleton2 instance;

        private Singleton2() {
            System.out.println("懒汉单例");
        }


        //TODO 线程安全  但是比较影响性能  synchronized同步粒度太大
        public static synchronized Singleton2 getInstance() {
            if (instance == null) {
                instance = new Singleton2();
            }
            return instance;
        }
    }

    //    //  //TODO 懒汉式安全
    static class Singleton3 {

        private static Singleton3 instance;

        private Singleton3() {
            System.out.println("懒汉单例");
        }


        //TODO 线程安全  使用双重判空加锁 优化synchronized性能
        //TODO 缩小synchronize范围
        //TODO 需要双重判空的原因：
        //TODO  new Singleton3()实际上是三个步骤：
        //TODO  1.分配Singleton3对象
        //TODO  2.调用构造方法初始化成员字段
        //TODO  3.将Singleton3对象赋值给instance  这三个步骤在jvm中可以是乱序的也就是说可能指令重排，就不能保证【2】在【3】之前执行 所以可能导致双重判空失效
        public static Singleton3 getInstance() {
            if (instance == null) {
                synchronized (Singleton3.class) {
                    if (instance == null) {
                        instance = new Singleton3();
                    }
                }
            }
            return instance;
        }
    }


    //  //TODO 懒汉式安全之双重判空优化
    static class Singleton4 {

        //TODO volatile关键字禁止指令重排，JDK1.5之后出的关键字
        //TODO 保证【1】--》【2】---》【3】指令顺序执行
        private volatile static Singleton4 instance = null;

        private Singleton4() {
            System.out.println("懒汉单例");
        }


        public static Singleton4 getInstance() {
            if (instance == null) {
                synchronized (Singleton4.class) {
                    if (instance == null) {
                        instance = new Singleton4();
                    }
                }
            }
            return instance;
        }
    }


    //
//    //TODO 饿汉式单例，等不及了，还没用到就开始创建实例
//    //TODO 线程安全
    static class Singleton5 {

        private static Singleton5 instance = new Singleton5();

        private Singleton5() {
            System.out.println("饿汉式单例");
        }


        //TODO 线程安全
        public static Singleton5 getInstance() {

            return instance;
        }
    }

    //TODO 线程安全
    //TODO JVM 第一次去加载Singleton6的时候，还不会去加载Holder
    //TODO 当调用getInstance方法时，JVM才回去加载Holder
    //TODO 相当于懒汉式加载不用synchronized
    static class Singleton6 {

        private Singleton6() {

        }

        public static Singleton6 getInstance() {

            return Holder.instance;
        }

        static class Holder {
            private static Singleton6 instance = new Singleton6();

        }
    }


    //TODO 使用枚举实现单例，线程安全
    //TODO 反序列化也不怕
    static enum Singleton7 {
        INSTANCE;
    }

    //TODO  使用容器实现单例 线程不安全
    static class ServiceManager {
        private static Map<String, Object> objMap = new HashMap<>();
        public static  void registerService(String key,Object obj){
            if(!objMap.containsKey(key)){
                objMap.put(key,obj);
            }
        }

        public static  Object getService(String key){
            return objMap.get(key);
        }

    }


}
