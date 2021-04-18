package com.module.plugin.cpt

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import com.module.plugin.cpt.bean.CptJarPathConfig
import com.module.plugin.cpt.util.ScanSetting
import org.gradle.api.Plugin
import org.gradle.api.Project

class CptPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
//        println("--------CptPlugin--------")

//        boolean isApp = project.getPlugins.hasPlugin(AppPlugin.class);
//        boolean isLibrary = project.getPlugins.hasPlugin("com.android.library");
        def isApp = project.plugins.withType(AppPlugin)
        def isLibrary = project.plugins.withType(LibraryPlugin)
//        println("isApp " + isApp + " isLibrary " + isLibrary)

        //生成代码取决于App工程 还是 Library工程
        //一、getScopes范围不同
        // 1.app工程 ，TransformManager.SCOPE_FULL_PROJECT
        // 2.Library工程，TransformManager.PROJECT_ONLY
        // 二、写入代码方式不同
        // 1.插件编译app壳工程，则需要将代码动态插入到app_core.jar的ServiceManager.register()方法中，并重写打包；
        // 2.插件编译app_coreLibray工程 ，则需要将代码写入ServiceManager.register()方法中.class文件即可

        def library = isApp ? project.extensions.getByType(AppExtension.class) : project.extensions.getByType(LibraryExtension.class)
        def transform = new CptTransform(project)
        transform.isApp = isApp

        ArrayList<ScanSetting> list = new ArrayList<>(1)
        list.add(new ScanSetting('IService'))
        CptTransform.registerList = list

        library.registerTransform(transform)

        // 通过Extension的方式传递将要被注入的自定义代码
        CptJarPathConfig extension = project.extensions.create("cptJarPathConfig", CptJarPathConfig)
        project.afterEvaluate {

            CptTransform.cptJarPathConfig = extension;
        }
    }
}