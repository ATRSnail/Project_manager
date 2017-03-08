package com.project.project_manager.mvp.entity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/2
 */

public class TaskDictionaryBean implements Serializable{


    /**
     * id : 315
     * utime : 1484557552000
     * userId : 1
     * name : 环节C
     * seq : 6
     * parent : 0
     * code : gcjg-00012
     * taskCode :
     * ctime : 1484196816000
     */

    private int id;
    private long utime;
    private String userId;
    private String name;
    private int seq;
    private int parent;
    private String code;
    private String taskCode;
    private long ctime;

    public TaskDictionaryBean(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "TaskDictionary{" +
                "id=" + id +
                ", utime=" + utime +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", seq=" + seq +
                ", parent=" + parent +
                ", code='" + code + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", ctime=" + ctime +
                '}';
    }
}
