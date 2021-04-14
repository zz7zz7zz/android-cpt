package com.module.components;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;

public final class ComponentServiceManager {

    private static final HashMap<String,Class> map  = new HashMap<>();

    static {
        register();
    }

//    public static void register(String component, Class clazz){
//        map.put(component,clazz);
//    }

    private static void register(){
//        map.put(com.module.components.video.IVideoService.MODULE,com.module.components.video.IVideoService.class);
//        map.put(com.module.components.shopping.IShoppingService.MODULE,com.module.components.shopping.IShoppingService.class);
//        map.put(com.module.components.app.IAppService.MODULE,com.module.components.app.IAppService.class);
//        map.put(com.module.components.integrate.IIntegrateService.MODULE,com.module.components.integrate.IIntegrateService.class);
//        map.put(com.module.components.game.IGameService.MODULE,com.module.components.game.IGameService.class);
//        map.put(com.module.components.news.INewsService.MODULE,com.module.components.news.INewsService.class);
//        map.put(com.module.components.im.IIMService.MODULE,com.module.components.im.IIMService.class);
//        Log.v("ComponentServiceManager","---------- componetSize " + map.size());
    }


    /**
     * 通过组件名来找到组件Service
     * @param component 组件名，如:app_im
     * @return
     */
    public static IComponentService getComponentByName(String component){
        return !TextUtils.isEmpty(component) ? (IComponentService) getComponentByClass(map.get(component)) : null;
    }

    /**
     * 通过服务类名来找到组件Service , 未找到会有一个空实现的默认服务
     * @param clazz 服务类名，如IIMService.class
     * @param <T>
     * @return
     */
    public static<T> T getComponentByClass(Class<T> clazz){
        return getComponentByClass(clazz,true);
    }

    public static<T> T getComponentByClass(Class<T> clazz, boolean isCreatedDefault){
//        if(com.module.components.video.IVideoService.class.equals(clazz)){
//            com.module.components.video.IVideoService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.video.IVideoService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.video.IVideoService.DEFAULT : null);
//        }else if(com.module.components.shopping.IShoppingService.class.equals(clazz)){
//            com.module.components.shopping.IShoppingService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.shopping.IShoppingService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.shopping.IShoppingService.DEFAULT : null);
//        }else if(com.module.components.app.IAppService.class.equals(clazz)){
//            com.module.components.app.IAppService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.app.IAppService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.app.IAppService.DEFAULT : null);
//        }else if(com.module.components.integrate.IIntegrateService.class.equals(clazz)){
//            com.module.components.integrate.IIntegrateService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.integrate.IIntegrateService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.integrate.IIntegrateService.DEFAULT : null);
//        }else if(com.module.components.game.IGameService.class.equals(clazz)){
//            com.module.components.game.IGameService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.game.IGameService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.game.IGameService.DEFAULT : null);
//        }else if(com.module.components.news.INewsService.class.equals(clazz)){
//            com.module.components.news.INewsService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.news.INewsService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.news.INewsService.DEFAULT : null);
//        }else if(com.module.components.im.IIMService.class.equals(clazz)){
//            com.module.components.im.IIMService ret =  com.alibaba.android.arouter.launcher.ARouter.getInstance().build(com.module.components.im.IIMService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : (isCreatedDefault ? com.module.components.im.IIMService.DEFAULT : null);
//        }
        return null;
    }
}
