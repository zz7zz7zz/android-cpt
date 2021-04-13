package com.module.plugin.cpt.bean;


class CptJarPathConfig {

    String arouterPath
    String fragmentPath

    @Override
    public String toString() {
        return "CptJarPathConfig{" +
                "arouterPath='" + arouterPath + '\'' +
                ", fragmentPath='" + fragmentPath + '\'' +
                '}'
    }
}