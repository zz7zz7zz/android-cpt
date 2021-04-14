package com.module.plugin.cpt.util;

class ScanSetting {

    static final String FLITER_CLASS_NAME_START = 'com/module/service'
    static final String FLITER_CLASS_NAME_END = 'Service.class'

    //往哪个类的哪个方法，添加什么方法/添加其它方法体
    static final String GENERATE_TO_CLASS_FILE_NAME = 'com/module/service/ServiceManager.class'
    static final String GENERATE_TO_METHOD_NAME_REGISTER = 'register'
    static final String GENERATE_TO_METHOD_NAME_GETCOMPONENTBYCLASS = 'getService'

    private static final INTERFACE_PACKAGE_NAME = 'com/module/service/'

    String interfaceName = ''
    File fileContainsInitClass
    ArrayList<String> classList = new ArrayList<>()

    ScanSetting(String interfaceName){
        this.interfaceName = INTERFACE_PACKAGE_NAME + interfaceName
    }
}
