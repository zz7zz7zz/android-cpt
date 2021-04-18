package com.lib.pay.core.service;

public class PayServiceInfo {

        private String name;         //服务名称　
        private String  serviceImplName;

        private Class  serviceImpl;  //服务接口的实现类

        public PayServiceInfo(String name, String serviceImplName) {
                this.name = name;
                this.serviceImplName = serviceImplName;

                try{
                     serviceImpl = Class.forName(serviceImplName);
                }catch (Exception e){
                     e.printStackTrace();
                }
        }

        public String getName() {
                return name;
        }

        public Class getServiceImpl() {
                return serviceImpl;
        }

        @Override
        public String toString() {
                return "ServiceInfo{" +
                        "name='" + name + '\'' +
                        ", serviceImpl=" + serviceImpl +
                        '}';
        }
}
