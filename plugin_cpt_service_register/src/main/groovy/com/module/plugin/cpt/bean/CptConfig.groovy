package com.module.plugin.cpt.bean;


class CptConfig {

    String applicationName
    String arouterPath
    String fragmentPath

    @Override
    public String toString() {
        return "CptConfig{" +
                "applicationName='" + applicationName + '\'' +
                ", arouterPath='" + arouterPath + '\'' +
                ", fragmentPath='" + fragmentPath + '\'' +
                '}'
    }
}