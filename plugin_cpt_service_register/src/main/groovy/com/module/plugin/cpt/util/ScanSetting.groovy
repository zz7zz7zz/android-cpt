package com.module.plugin.cpt.util;

class ScanSetting {

    //---------------------------------------------------------
    //服务类后缀
    static final String FLITER_CLASS_NAME_START = 'com/module/service'
    static final String FLITER_CLASS_NAME_END = 'Service.class'

    //服务实现类后缀
    static final String FLITER_CLASS_NAME_SERVICE_IMPL_END = 'ServiceImpl.class'


    //往哪个类的哪个方法，添加什么方法/添加其它方法体
    static final String GENERATE_TO_CLASS_FILE_NAME = 'com/module/service/ServiceManager.class'
    static final String GENERATE_TO_CLASS_NAME= 'com/module/service/ServiceManager'
    static final String REGISTER_METHOD_NAME = 'loadServiceToMap'
    static final String MODULE_NAME_OF_FIELD = 'MODULE'

    private static final INTERFACE_PACKAGE_NAME = 'com/module/service/'

    String interfaceName = ''

    //---------------------------------------------------------
    //用户定义的服务接口类IxxxService，继承自IService
    ArrayList<String> serviceList = new ArrayList<>()

    //IxxxService具体实现类,IxxxServiceImpl
    HashMap<String,String> serviceImplMap = new HashMap<>()

    //接口类IxxxService具体模块名，MODULE
    HashMap<String,String> serviceModuleNameMap = new HashMap<>()

    HashMap<String,String> allMap = new HashMap<>()

    ScanSetting(String interfaceName){
        this.interfaceName = INTERFACE_PACKAGE_NAME + interfaceName
    }
}
