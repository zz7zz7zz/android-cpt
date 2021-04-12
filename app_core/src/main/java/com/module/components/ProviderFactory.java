package com.module.components;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.app.IAppProvider;
import com.module.components.game.IGameProvider;
import com.module.components.im.IIMProvider;
import com.module.components.integrate.IIntegrateProvider;
import com.module.components.news.INewsProvider;
import com.module.components.shopping.IShoppingProvider;
import com.module.components.video.IVideoProvider;

public class ProviderFactory {

    /**
     * 通过组件名（如:app_im）来找到Provider
     * @param component
     * @return
     */
    public static IComponentsProvider getComponentByName(String component){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IComponentsProvider)ARouter.getInstance().build("/"+component.substring(component.indexOf("_")+1)+"/P").navigation();
    }

    /**
     * 通过服务类名来找到Provider
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T getComponentByClass(Class<T> clazz){
//        if(clazz.equals(IAppProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IAppProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IAppProvider.DEFAULT;
//        }else if(clazz.equals(IGameProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IGameProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IGameProvider.DEFAULT;
//        }else if(clazz.equals(IIMProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IIMProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IIMProvider.DEFAULT;
//        }else if(clazz.equals(IIntegrateProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IIntegrateProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IIntegrateProvider.DEFAULT;
//        }else if(clazz.equals(INewsProvider.class)){
//            T ret = (T) ARouter.getInstance().build(INewsProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : INewsProvider.DEFAULT;
//        }else if(clazz.equals(IShoppingProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IShoppingProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IShoppingProvider.DEFAULT;
//        }else if(clazz.equals(IVideoProvider.class)){
//            T ret = (T) ARouter.getInstance().build(IVideoProvider.PROVIDER_MAIN).navigation();
//            return null != ret ? ret : IVideoProvider.DEFAULT;
//        }
        return null;
    }
}
