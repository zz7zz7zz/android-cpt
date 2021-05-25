package com.module.im.api;

public class FiddlerResponse {

    public String Tags;
    public String Text;
    public String Url;

    @Override
    public String toString() {
        return "FiddlerResponse{" +
                "Tags='" + Tags + '\'' +
                ", Text='" + Text + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
