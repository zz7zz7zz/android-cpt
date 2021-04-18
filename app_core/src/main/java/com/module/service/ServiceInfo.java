package com.module.service;

public class ServiceInfo {

        private String name;         //服务名称　
        private String  serviceName;
        private String  serviceImplName;

        private Class  service;      //服务暴露的接口
        private Class  serviceImpl;  //服务接口的实现类

        public ServiceInfo(String name, String serviceName, String serviceImplName) {
                this.name = name;
                this.serviceName = serviceName;
                this.serviceImplName = serviceImplName;

                try{
                        service = Class.forName(serviceName);
                        serviceImpl = Class.forName(serviceImplName);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        public String getName() {
                return name;
        }

        public Class getService() {
                return service;
        }

        public Class getServiceImpl() {
                return serviceImpl;
        }

        @Override
        public String toString() {
                return "ServiceInfo{" +
                        "name='" + name + '\'' +
                        ", service=" + service +
                        ", serviceImpl=" + serviceImpl +
                        '}';
        }
}
