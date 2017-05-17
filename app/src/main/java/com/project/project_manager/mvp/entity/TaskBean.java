package com.project.project_manager.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.project_manager.mvp.ui.activity.ConstructionTActivity;
import com.project.project_manager.mvp.ui.activity.VerifyDetailActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/2
 */

public class TaskBean implements Serializable{
    public static final String TASK_KEY = "task_key";
    /**
     * uid : 1
     * status : 5
     * nodeName : 和稀泥
     * companyType : 0
     * pid : 1
     * taskDictionary : {"id":315,"utime":1484557552000,"userId":"1","name":"环节C","seq":6,"parent":0,"code":"gcjg-00012","taskCode":"","ctime":1484196816000}
     * nodeCode : 0003
     */

    private String uid;
    private String status;
    private String nodeName;
    private String companyType;
    private int taskUserId;
    private int pid;
    private long projectTaskId;
    private TaskDictionaryBean taskDictionary;
    private String nodeCode;

    public void intentToNext(Context context){
        Intent intent = new Intent(context,VerifyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TASK_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void intentToNextT(Context context){
        Intent intent = new Intent(context,ConstructionTActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TASK_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(long projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public TaskDictionaryBean getTaskDictionary() {
        return taskDictionary;
    }

    public void setTaskDictionary(TaskDictionaryBean taskDictionary) {
        this.taskDictionary = taskDictionary;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public int getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(int taskUserId) {
        this.taskUserId = taskUserId;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "uid='" + uid + '\'' +
                ", status='" + status + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", taskUserId=" + taskUserId +
                ", pid=" + pid +
                ", projectTaskId=" + projectTaskId +
                ", taskDictionary=" + taskDictionary +
                ", nodeCode='" + nodeCode + '\'' +
                '}';
    }
}
