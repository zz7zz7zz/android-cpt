package com.module.integrate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.service.integrate.IIntegrateService;

import java.lang.ref.WeakReference;

@Route(path = IIntegrateService.PROVIDER_MAIN, name = IIntegrateService.MODULE)
public class IntegrateServiceImpl implements IIntegrateService {

    private static final String TAG = "IntegrateServiceImpl";
    private Context context;
    private WeakReference<Fragment> fragmentWeakReference;
    private WeakReference<View> viewWeakReference;

    @Override
    public View getComponentTabView(Context context, boolean isCreatedIfNull) {
        View ret = (null != viewWeakReference) ? viewWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
            ((TextView)(ret.findViewById(R.id.moudle_name))).setText(getComponentName());
            ((ImageView)(ret.findViewById(R.id.moudle_icon))).setBackgroundResource(getComponentIconResId());
            viewWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public Fragment getComponentMainFragment(boolean isCreatedIfNull) {
        Fragment ret = (null != fragmentWeakReference) ? fragmentWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = new IntegrateMainFragment();
            fragmentWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public void startComponentMainActivity(Context context) {
        Intent mIntent = new Intent(context, IntegrateMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getComponentName() {
        return context.getString(R.string.integrate_name);
    }

    @Override
    public int getComponentIconResId() {
        return R.drawable.integrate_icon_selector;
    }

    @Override
    public String getIntegrateTasks() {
        Log.v(TAG,"getIntegrateTasks ");
        return null;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onComponentEnter() {
        Log.v(TAG,"onModuleEnter ");
    }

    @Override
    public void onComponentExit() {
        Log.v(TAG,"onModuleExit ");
        if(null != fragmentWeakReference){
            fragmentWeakReference.clear();
            fragmentWeakReference = null;
        }

        if(null != viewWeakReference){
            viewWeakReference.clear();
            viewWeakReference = null;
        }

        //help gc
        System.gc();
    }
}
