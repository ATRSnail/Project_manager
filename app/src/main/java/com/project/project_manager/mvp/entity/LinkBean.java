package com.project.project_manager.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.project_manager.mvp.ui.activity.NodesActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class LinkBean implements Serializable{
    public static final String LINK_KEY = "link_key";
    public static final String P_KEY = "p_id";
    /**
     * id : 320
     * utime : null
     * status : 0
     * taskType : 0
     * name : null
     * nodeName : G环节B
     * pid : null
     * taskCode : aOjrE1484895724042
     * ctime : 1484895724000
     * nodeCode : null
     */

    private int id;
    private Object utime;
    private String status;
    private String taskType;
    private Object name;
    private String nodeName;
    private Object pid;
    private String taskCode;
    private long ctime;
    private Object nodeCode;

    public void intentToNext(Context context,String pId){
        Intent intent = new Intent(context,NodesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LINK_KEY,this);
        bundle.putString(P_KEY,  pId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getUtime() {
        return utime;
    }

    public void setUtime(Object utime) {
        this.utime = utime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public Object getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(Object nodeCode) {
        this.nodeCode = nodeCode;
    }

    @Override
    public String toString() {
        return "LinkBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", status='" + status + '\'' +
                ", taskType='" + taskType + '\'' +
                ", name=" + name +
                ", nodeName='" + nodeName + '\'' +
                ", pid=" + pid +
                ", taskCode='" + taskCode + '\'' +
                ", ctime=" + ctime +
                ", nodeCode=" + nodeCode +
                '}';
    }
}
