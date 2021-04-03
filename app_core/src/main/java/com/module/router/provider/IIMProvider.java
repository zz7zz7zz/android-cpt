package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IIMConsts;

public interface IIMProvider extends IModuleProvider {

    String getMessage();

    void sendMessage(String msg);

    public static IIMProvider get(){
        return (IIMProvider) ARouter.getInstance().build(IIMConsts.Provider.MAIN).navigation();
    }
}
