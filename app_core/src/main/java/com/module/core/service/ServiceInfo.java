package com.module.core.service;


public final class ServiceInfo {

        private String  name;         //服务名称　
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

        public ServiceInfo(String name, Class service, Class serviceImpl) {
                this.name = name;
                this.service = service;
                this.serviceImpl = serviceImpl;

                this.serviceName = service.getName();
                this.serviceImplName = serviceImpl.getName();
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
                        ", serviceName='" + serviceName + '\'' +
                        ", serviceImplName='" + serviceImplName + '\'' +
                        ", service=" + service +
                        ", serviceImpl=" + serviceImpl +
                        '}';
        }
}
