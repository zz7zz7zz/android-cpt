package com.module.plugin.cpt

import com.android.build.gradle.LibraryExtension
import com.module.plugin.cpt.bean.CptJarPathConfig
import com.module.plugin.cpt.util.ScanSetting
import org.gradle.api.Plugin
import org.gradle.api.Project

class CptPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
//        println("--------CptPlugin--------")

        def library = project.extensions.getByType(LibraryExtension.class)
        def transform = new CptTransform(project)

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