package com.module.core.utils;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class ServiceProxy {

    //key:被代理对象; value: 封装代理的对象
    private static HashMap<Object,Object> map = new HashMap<>();

    public static <T> T get(String tag,T t) {
        Object proxyProvider = map.get(t);
        if(null != proxyProvider){
            return (T) proxyProvider;
        }

        // 1.获取对应的ClassLoader
        ClassLoader classLoader = t.getClass().getClassLoader();

        // 2.获取A 所实现的所有接口
        Class[] interfaces = t.getClass().getInterfaces();

        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler handler = new InvocationHandlerImp(tag,t);
		/*
		  4.根据上面提供的信息，创建代理对象 在这个过程中，
                         a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
		         b.然后根据相应的字节码转换成对应的class，
                         c.然后调用newInstance()创建实例
		 */
        T ret = (T) Proxy.newProxyInstance(classLoader, interfaces, handler);

        map.put(t, ret);
        return ret;
    }


    public static final String PROMPT_COMPONENT_NOT_FOUND = "Service 未找到，可能的原因是: \n" +
            "1.没有将组件代码打包进Apk中; \n" +
            "2.已实现对外提供服务类，但没有添加注解,如 @Route(path = \"xxx\", name = \"yyy\");\n" +
            "3.未实现对外提供服务类; \n"+
            "4.本地组件配置/服务器组件配置，未启用组件; \n";

    private static final class InvocationHandlerImp implements InvocationHandler {

        private String TAG ;
        private Object target;

        public InvocationHandlerImp(String TAG, Object target) {
            this.TAG = TAG;
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            Log.e(TAG, PROMPT_COMPONENT_NOT_FOUND);
            return method.invoke(target,objects);
        }

        // 生成代理类
        public Object create() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }
    }
}
