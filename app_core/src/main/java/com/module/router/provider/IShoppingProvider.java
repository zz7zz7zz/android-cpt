package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IShoppingConsts;

public interface IShoppingProvider extends IModuleProvider {

    String getGoodInfo();

    public static IShoppingProvider get(){
        return (IShoppingProvider) ARouter.getInstance().build(IShoppingConsts.Provider.MAIN).navigation();
    }
}
