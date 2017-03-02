package com.project.project_manager.mvp.entity;

import android.content.Context;
import android.content.Intent;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */

public class FileBean {

    private String name;
    private int imgSrc;
    private Class<?> intentClass;

    public FileBean(String name, int imgSrc, Class<?> intentClass) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.intentClass = intentClass;
    }

    public void intentToAct(Context context){
        context.startActivity(new Intent(context,intentClass));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class<?> intentClass) {
        this.intentClass = intentClass;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", imgSrc=" + imgSrc +
                ", intentClass=" + intentClass +
                '}';
    }
}
