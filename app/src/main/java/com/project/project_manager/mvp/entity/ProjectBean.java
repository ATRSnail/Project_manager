package com.project.project_manager.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.project_manager.mvp.ui.activity.AddNeedActivity;
import com.project.project_manager.mvp.ui.activity.LinkActivity;
import com.project.project_manager.mvp.ui.activity.ProjectItemsActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class ProjectBean implements Serializable{
     public static final String PROJECT_KEY = "project_key";

    /**
     * uid : 1
     * id : 1
     * utime : 1488176955000
     * projectPeriod : 150
     * projectCode : 30301122
     * status : 1
     * describe : fdsafdasfdasfasd
     * projectManagerId : cVxi2w7X08SE76YAHu1EE7oFeddrzdI7
     * ctime : 1484127542000
     * projectName : 项目测试12
     * projectBeginTime : 1484127535000
     */

    private String uid;
    private int id;
    private long utime;
    private int projectPeriod;
    private String projectCode;
    private String status;
    private String describe;
    private String projectManagerId;
    private long ctime;
    private String projectName;
    private long projectBeginTime;

    public void intentToNext(Context context){
        Intent intent = new Intent(context,LinkActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROJECT_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void intentToAdd(Context context){
        Intent intent = new Intent(context,AddNeedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROJECT_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void intentToTask(Context context){
        Intent intent = new Intent(context,ProjectItemsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROJECT_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public int getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(int projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getProjectBeginTime() {
        return projectBeginTime;
    }

    public void setProjectBeginTime(long projectBeginTime) {
        this.projectBeginTime = projectBeginTime;
    }

    @Override
    public String toString() {
        return "ProjectBean{" +
                "uid='" + uid + '\'' +
                ", id=" + id +
                ", utime=" + utime +
                ", projectPeriod=" + projectPeriod +
                ", projectCode='" + projectCode + '\'' +
                ", status='" + status + '\'' +
                ", describe='" + describe + '\'' +
                ", projectManagerId='" + projectManagerId + '\'' +
                ", ctime=" + ctime +
                ", projectName='" + projectName + '\'' +
                ", projectBeginTime=" + projectBeginTime +
                '}';
    }
}
