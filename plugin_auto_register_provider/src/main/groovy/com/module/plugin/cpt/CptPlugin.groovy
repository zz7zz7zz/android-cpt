package com.module.plugin.cpt

import com.android.build.gradle.LibraryExtension
import com.module.plugin.cpt.util.ScanSetting
import org.gradle.api.Plugin
import org.gradle.api.Project

class CptPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        print("--------CptPlugin--------")
//        def cptJarPathConfig = project.extensions.create("cptJarPathConfig", CptJarPathConfig)
//        println("CptPlugin cptJarPathConfig "+cptJarPathConfig.toString());
//        println("CptPlugin cptJarPathConfig.arouterPath "+cptJarPathConfig.arouterPath)

        def library = project.extensions.getByType(LibraryExtension.class)
        def transform = new CptTransform(project)

        ArrayList<ScanSetting> list = new ArrayList<>(1)
        list.add(new ScanSetting('IComponentService'))
        CptTransform.registerList = list

        library.registerTransform(transform)

    }
}