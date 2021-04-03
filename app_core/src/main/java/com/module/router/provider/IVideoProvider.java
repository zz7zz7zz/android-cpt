package com.module.router.provider;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IVideoConsts;

public interface IVideoProvider extends IModuleProvider {

    void playVideo(Context context, String msg);

    public static IVideoProvider get(){
        return (IVideoProvider)ARouter.getInstance().build(IVideoConsts.Provider.MAIN).navigation();
    }

}
