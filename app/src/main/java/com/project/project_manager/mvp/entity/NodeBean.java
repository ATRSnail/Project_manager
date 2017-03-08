package com.project.project_manager.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.project_manager.mvp.ui.activity.ConstructionActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class NodeBean implements Serializable{
    public static final String NODE_KEY = "node_key";

    /**
     * id : 242
     * utime : 1484812015000
     * status : 0
     * taskType : 2
     * name : null
     * nodeName : 和稀泥
     * pid : 1
     * taskCode : lMnx01484639702952
     * ctime : 1484812015000
     * nodeCode : 0003
     */

    private int id;
    private long utime;
    private String status;
    private String taskType;
    private Object name;
    private String nodeName;
    private int pid;
    private String taskCode;
    private long ctime;
    private String nodeCode;

    public void intentToNext(Context context){
        Intent intent = new Intent(context,ConstructionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(NODE_KEY,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
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

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    @Override
    public String toString() {
        return "NodeBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", status='" + status + '\'' +
                ", taskType='" + taskType + '\'' +
                ", name=" + name +
                ", nodeName='" + nodeName + '\'' +
                ", pid=" + pid +
                ", taskCode='" + taskCode + '\'' +
                ", ctime=" + ctime +
                ", nodeCode='" + nodeCode + '\'' +
                '}';
    }
}
