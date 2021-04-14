package com.module.service;

import android.text.TextUtils;

import java.util.HashMap;

public final class ServiceManager {

    private static final HashMap<String,Class> map  = new HashMap<>();

    static {
        register();
    }

//    public static void register(String component, Class clazz){
//        map.put(component,clazz);
//    }

    private static void register(){
//        map.put(com.module.service.video.IVideoService.MODULE,com.module.service.video.IVideoService.class);
//        map.put(com.module.service.shopping.IShoppingService.MODULE,com.module.service.shopping.IShoppingService.class);
//        map.put(com.module.service.app.IAppService.MODULE,com.module.service.app.IAppService.class);
//        map.put(com.module.service.integrate.IIntegrateService.MODULE,com.module.service.integrate.IIntegrateService.class);
//        map.put(com.module.service.game.IGameService.MODULE,com.module.service.game.IGameService.class);
//        map.put(com.module.service.news.INewsService.MODULE,com.module.service.news.INewsService.class);
//        map.put(com.module.service.im.IIMService.MODULE,com.module.service.im.IIMService.class);
//        Log.v("ComponentServiceManager","---------- componetSize " + map.size());
    }


    /**
     * 通过组件名来找到组件Service
     * @param component 组件名，如:app_im
     * @return
     */
    public static IService getService(String component){
        return !TextUtils.isEmpty(component) ? (IService) getService(map.get(component)) : null;
    }

    /**
     * 通过服务类名来找到组件Service
     * @param clazz 服务类名，如IIMService.class
     * @return 找到返回；未找到返回null
     */
    public static<T> T getService(Class<T> clazz){
        return getService(clazz,true);
    }

    /**
     * 通过服务类名来找到组件Service
     * @param clazz clazz 服务类名，如IIMService.class
     * @param isCreatedDefault 在未找到服务的情况下，是否返回一个空实现的服务
     * @param <T>
     * @return 找到返回；未找到，如果isCreatedDefault true,返回默认空实现服务，isCreatedDefault false，返回null
     */
    public static<T> T getService(Class<T> clazz, boolean isCreatedDefault){
//        if(com.module.service.video.IVideoService.class.equals(clazz)){
//            com.module.service.video.IVideoService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.video.IVideoService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.video.IVideoService.DEFAULT : null);
//        }else if(com.module.service.shopping.IShoppingService.class.equals(clazz)){
//            com.module.service.shopping.IShoppingService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.shopping.IShoppingService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.shopping.IShoppingService.DEFAULT : null);
//        }else if(com.module.service.app.IAppService.class.equals(clazz)){
//            com.module.service.app.IAppService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.app.IAppService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.app.IAppService.DEFAULT : null);
//        }else if(com.module.service.integrate.IIntegrateService.class.equals(clazz)){
//            com.module.service.integrate.IIntegrateService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.integrate.IIntegrateService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.integrate.IIntegrateService.DEFAULT : null);
//        }else if(com.module.service.game.IGameService.class.equals(clazz)){
//            com.module.service.game.IGameService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.game.IGameService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.game.IGameService.DEFAULT : null);
//        }else if(com.module.service.news.INewsService.class.equals(clazz)){
//            com.module.service.news.INewsService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.news.INewsService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.news.INewsService.DEFAULT : null);
//        }else if(com.module.service.im.IIMService.class.equals(clazz)){
//            com.module.service.im.IIMService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.service.im.IIMService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.service.im.IIMService.DEFAULT : null);
//        }
        return null;
    }
}
