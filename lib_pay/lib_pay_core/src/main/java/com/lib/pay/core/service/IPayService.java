package com.lib.pay.core.service;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IPayService extends IProvider {
        void pay(String sku);
}
