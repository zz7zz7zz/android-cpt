package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.INewsConsts;

public interface INewsProvider extends IModuleProvider {

    String getNewsList();

    public static INewsProvider get(){
        return (INewsProvider) ARouter.getInstance().build(INewsConsts.Provider.MAIN).navigation();
    }
}
