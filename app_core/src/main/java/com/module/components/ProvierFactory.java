package com.module.components;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.app.IAppProvider;
import com.module.components.game.IGameProvider;
import com.module.components.im.IIMProvider;
import com.module.components.integrate.IIntegrateProvider;
import com.module.components.news.INewsProvider;
import com.module.components.shopping.IShoppingProvider;
import com.module.components.video.IVideoProvider;

public class ProvierFactory {

    /**
     * 通过组件名（如:app_im）来找到Provider
     * @param component
     * @return
     */
    public static IComponentsProvider get(String component){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IComponentsProvider)ARouter.getInstance().build("/"+component.substring(component.indexOf("_")+1)+"/P").navigation();
    }

    /**
     * 通过服务类名来找到Provider
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T get(Class<T> clazz){
        if(clazz.equals(IAppProvider.class)){
            return (T) IAppProvider.get();
        }else if(clazz.equals(IGameProvider.class)){
            return (T) IGameProvider.get();
        }else if(clazz.equals(IIMProvider.class)){
            return (T) IIMProvider.get();
        }else if(clazz.equals(IIntegrateProvider.class)){
            return (T) IIntegrateProvider.get();
        }else if(clazz.equals(INewsProvider.class)){
            return (T) INewsProvider.get();
        }else if(clazz.equals(IShoppingProvider.class)){
            return (T) IShoppingProvider.get();
        }else if(clazz.equals(IVideoProvider.class)){
            return (T) IVideoProvider.get();
        }
        return null;
    }
}
