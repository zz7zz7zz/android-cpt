package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IIntegrateConsts;

public interface IIntegrateProvider extends IModuleProvider {

    String getIntegrateTasks();

    public static IIntegrateProvider get(){
        return (IIntegrateProvider) ARouter.getInstance().build(IIntegrateConsts.Provider.MAIN).navigation();
    }
}
