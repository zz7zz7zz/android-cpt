package com.module.core.service;

import android.text.TextUtils;
import android.util.Log;

import com.module.core.BaseApplication;

import java.util.HashMap;

public final class ServiceManager {

    private static final HashMap<String, ServiceInfo> nameService = new HashMap<>();//元数据集合,组件名为模块名
    private static final HashMap<Class, ServiceInfo> classService = new HashMap<>();//元数据集合,组件类为服务类名
    private static boolean registerByPlugin = false;

    private static final HashMap<Class, Object> sMap = new HashMap<>();//实例对象


    private static void loadServiceToMap(){
        registerByPlugin = false;
    }

    //没有使用插件的情况下，才用反射的方法
    private static void degradeLoadServiceToMap(){
        registerService(":app_video","com.module.core.service.video.IVideoService","com.module.video.VideoServiceImpl");
        registerService(":app_shopping","com.module.core.service.shopping.IShoppingService","com.module.shopping.ShoppingServiceImpl");
        registerService(":app_integrate","com.module.core.service.integrate.IIntegrateService","com.module.integrate.IntegrateServiceImpl");
        registerService(":app_game","com.module.core.service.game.IGameService","com.module.game.GameServiceImpl");
        registerService(":app_news","com.module.core.service.news.INewsService","com.module.news.NewsServiceImpl");
        registerService(":app_im","com.module.core.service.im.IIMService","com.module.im.IMServiceImpl");
        registerService(":app","com.module.core.service.app.IAppService","com.module.main.AppServiceImpl");
    }

    private static void registerService(String name,String serviceName,String serviceImplName){
        ServiceInfo serviceInfo = new ServiceInfo(name,serviceName,serviceImplName);
        nameService.put(name,serviceInfo);
        classService.put(serviceInfo.getService(),serviceInfo);
    }

    private static void registerService(String name,Class clz,Class clz2){
        ServiceInfo serviceInfo = new ServiceInfo(name,clz,clz2);
        nameService.put(name,serviceInfo);
        classService.put(serviceInfo.getService(),serviceInfo);
    }

    private static<T> T createDefault(Class<? extends T> clazz){
        return null;
    }

    //没有使用插件的情况下，才用手动遍历的方法
    private static<T> T degradeCreateDefault(Class<? extends T> clazz){
        if(com.module.core.service.app.IAppService.class.equals(clazz)){
            return (T) com.module.core.service.app.IAppService.DEFAULT;
        }else if(com.module.core.service.game.IGameService.class.equals(clazz)){
            return (T) com.module.core.service.game.IGameService.DEFAULT;
        }else if(com.module.core.service.im.IIMService.class.equals(clazz)){
            return (T) com.module.core.service.im.IIMService.DEFAULT;
        }else if(com.module.core.service.integrate.IIntegrateService.class.equals(clazz)){
            return (T) com.module.core.service.integrate.IIntegrateService.DEFAULT;
        }else if(com.module.core.service.news.INewsService.class.equals(clazz)){
            return (T) com.module.core.service.news.INewsService.DEFAULT;
        }else if(com.module.core.service.shopping.IShoppingService.class.equals(clazz)){
            return (T) com.module.core.service.shopping.IShoppingService.DEFAULT;
        }else if(com.module.core.service.video.IVideoService.class.equals(clazz)){
            return (T) com.module.core.service.video.IVideoService.DEFAULT;
        }
        return null;
    }

    public static void init(){
        loadServiceToMap();

        Log.v("ServiceManager","registerByPlugin " + registerByPlugin + " nameService.size " + nameService.size());
        if(!registerByPlugin || nameService.size() == 0){
            degradeLoadServiceToMap();
        }

        Log.v("ServiceManager","------------- init start ------------");
        for (ServiceInfo info: classService.values()) {
            Log.v("ServiceManager",info.toString());
        }
        Log.v("ServiceManager","------------- init end ------------");
    }

    public static IService getService(String component){
        return getService(component,true);
    }

    public static IService getService(String component, boolean isCreatedDefault){
        ServiceInfo serviceInfo = nameService.get(component);
        if(null != serviceInfo){
            return !TextUtils.isEmpty(component) ? (IService) getService(serviceInfo.getService(),isCreatedDefault) : null;
        }
        return null;
    }

    public static<T> T getService(Class<? extends T> clazz){
        return getService(clazz,true);
    }

    public static<T> T getService(Class<? extends T> clazz, boolean isCreatedDefault){
        try{
            T ret = (T)sMap.get(clazz);
            if(null == ret){

                ServiceInfo serviceInfo = classService.get(clazz);
                if(null != serviceInfo){
                    Class serviceImplClass = serviceInfo.getServiceImpl();
                    if(null != serviceImplClass){
                        ret = (T)serviceImplClass.getConstructor().newInstance();
                    }
                }

                if(null == ret && isCreatedDefault){
                    ret = (T)createDefault(clazz);

                    if(null == ret){
                        ret = (T)degradeCreateDefault(clazz);
                    }
                }

                ((IService)(ret)).init(BaseApplication.getInstance());
            }

            if(null != ret){
                sMap.put(clazz,ret);
            }
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}