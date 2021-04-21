package com.module.service;

import android.text.TextUtils;
import android.util.Log;

import com.module.BaseApplication;

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
//        registerService(":app_video","com.module.service.video.IVideoService","com.module.video.VideoServiceImpl");
//        registerService(":app_shopping","com.module.service.shopping.IShoppingService","com.module.shopping.ShoppingServiceImpl");
//        registerService(":app_integrate","com.module.service.integrate.IIntegrateService","com.module.integrate.IntegrateServiceImpl");
//        registerService(":app_game","com.module.service.game.IGameService","com.module.game.GameServiceImpl");
//        registerService(":app_news","com.module.service.news.INewsService","com.module.news.NewsServiceImpl");
//        registerService(":app_im","com.module.service.im.IIMService","com.module.im.IMServiceImpl");
//        registerService(":app","com.module.service.app.IAppService","com.module.main.AppServiceImpl");
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
        if(com.module.service.video.IVideoService.class.equals(clazz)){
              return (T)com.module.service.video.IVideoService.DEFAULT;
        }else if(com.module.service.shopping.IShoppingService.class.equals(clazz)){
              return (T)com.module.service.shopping.IShoppingService.DEFAULT;
        }else if(com.module.service.app.IAppService.class.equals(clazz)){
              return (T)com.module.service.app.IAppService.DEFAULT;
        }else if(com.module.service.integrate.IIntegrateService.class.equals(clazz)){
              return (T)com.module.service.integrate.IIntegrateService.DEFAULT;
        }else if(com.module.service.game.IGameService.class.equals(clazz)){
              return (T)com.module.service.game.IGameService.DEFAULT;
        }else if(com.module.service.news.INewsService.class.equals(clazz)){
              return (T)com.module.service.news.INewsService.DEFAULT;
        }else if(com.module.service.im.IIMService.class.equals(clazz)){
              return (T)com.module.service.im.IIMService.DEFAULT;
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
