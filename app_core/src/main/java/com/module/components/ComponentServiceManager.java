package com.module.components;

import com.alibaba.android.arouter.launcher.ARouter;

public class ComponentServiceManager {

    /**
     * 通过组件名（如:app_im）来找到Provider
     * @param component
     * @return
     */
    public static IComponentService getComponentByName(String component){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IComponentService)ARouter.getInstance().build("/"+component.substring(component.indexOf("_")+1)+"/P").navigation();
    }

    /**
     * 通过服务类名来找到Provider
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T getComponentByClass(Class<T> clazz){
//        if(clazz.equals(IAppService.class)){
//            T ret = (T) ARouter.getInstance().build(IAppService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IAppService.DEFAULT;
//        }else if(clazz.equals(IGameService.class)){
//            T ret = (T) ARouter.getInstance().build(IGameService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IGameService.DEFAULT;
//        }else if(clazz.equals(IIMService.class)){
//            T ret = (T) ARouter.getInstance().build(IIMService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IIMService.DEFAULT;
//        }else if(clazz.equals(IIntegrateService.class)){
//            T ret = (T) ARouter.getInstance().build(IIntegrateService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IIntegrateService.DEFAULT;
//        }else if(clazz.equals(INewsService.class)){
//            T ret = (T) ARouter.getInstance().build(INewsService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : INewsService.DEFAULT;
//        }else if(clazz.equals(IShoppingService.class)){
//            T ret = (T) ARouter.getInstance().build(IShoppingService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IShoppingService.DEFAULT;
//        }else if(clazz.equals(IVideoService.class)){
//            T ret = (T) ARouter.getInstance().build(IVideoService.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IVideoService.DEFAULT;
//        }
        return null;
    }
}
