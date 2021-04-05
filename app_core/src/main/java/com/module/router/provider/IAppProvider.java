package com.module.router.provider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IAppConsts;
import com.module.router.consts.IVideoConsts;

public interface IAppProvider extends IModuleProvider{

    public static IVideoProvider get(){
        return (IVideoProvider) ARouter.getInstance().build(IAppConsts.Provider.MAIN).navigation();
    }

}
