package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IGameConsts;

public interface IGameProvider extends IModuleProvider{

    void startGame(String msg);

    public static IGameProvider get(){
        return (IGameProvider) ARouter.getInstance().build(IGameConsts.Provider.MAIN).navigation();
    }
}
