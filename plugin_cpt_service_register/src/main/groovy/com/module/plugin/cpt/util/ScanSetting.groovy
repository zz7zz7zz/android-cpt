package com.module.plugin.cpt.util;

class ScanSetting {

    static final String FLITER_CLASS_NAME_START = 'com/module/service'
    static final String FLITER_CLASS_NAME_END = 'Service.class'

    static final String FLITER_CLASS_NAME_SERVICE_IMPL_END = 'ServiceImpl.class'


    //往哪个类的哪个方法，添加什么方法/添加其它方法体
    static final String GENERATE_TO_CLASS_FILE_NAME = 'com/module/service/ServiceManager.class'
    static final String GENERATE_TO_METHOD_NAME_REGISTER = 'register'
    static final String GENERATE_TO_METHOD_NAME_GETCOMPONENTBYCLASS = 'getService'

    private static final INTERFACE_PACKAGE_NAME = 'com/module/service/'

    static final String GENERATE_TO_METHOD_NAME = 'loadServiceToMap'
    static final String GENERATE_TO_CLASS_NAME= 'com/module/service/ServiceManager'
    static final String REGISTER_METHOD_NAME = 'loadServiceToMap'

    static final String GENERATE_TO_CLASS_FILE_NAME2 = 'com/module/main/App.class'
//    static final String REGISTER_METHOD_NAME2 = 'onCreate'
    static final String REGISTER_METHOD_NAME2 = 'onInitComponentService'

    String interfaceName = ''
    File fileContainsInitClass

    //接口类IxxxService，继承自IService
    ArrayList<String> serviceList = new ArrayList<>()

    //IxxxService具体实现类,IxxxServiceImpl
    HashMap<String,String> serviceImplMap = new HashMap<>()

    HashMap<String,String> allMap = new HashMap<>()

    ScanSetting(String interfaceName){
        this.interfaceName = INTERFACE_PACKAGE_NAME + interfaceName
    }
}
