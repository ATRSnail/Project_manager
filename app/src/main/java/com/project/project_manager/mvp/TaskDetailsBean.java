package com.project.project_manager.mvp;

import com.project.project_manager.mvp.entity.ResourceBean;
import com.project.project_manager.mvp.entity.SysUserBean;
import com.project.project_manager.mvp.entity.TaskDictionaryBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/8
 */

public class TaskDetailsBean {


    /**
     * uid : xKdvuRqsPvUqORreODV6PPErOfMwqaWJ
     * sysUser : {"niceName":"陈1","utime":null,"imei":"1","state":"1","userEmail":"1@qq.com","ctime":1488851157000,"passWord":"0467eea3e0ca0b894c2d38de927022a5","phoneModel":"1","userType":"","id":"xKdvuRqsPvUqORreODV6PPErOfMwqaWJ","phoneNo":"13111111111","systemeType":"1","userName":"chen1","createUserId":""}
     * taskUserId : 3
     * taskType : 0
     * nodeName : 和稀泥
     * list : [{"id":50,"utime":null,"projectTaskId":318,"resourceUrl":"image201703081656541138.jpg","ctime":1488963414876,"resourceType":"0"},{"id":51,"utime":null,"projectTaskId":318,"resourceUrl":"video201703081656541180.mp4","ctime":1488963414883,"resourceType":"1"}]
     * pid : 15
     * companyName :
     * taskCode : QlPmb1488423507231
     * companyType :
     * opinion :
     * projectTaskId : 318
     * projectName : 动漫项目
     * taskDictionary : {"id":315,"utime":1484557552000,"userId":"1","name":"环节C","seq":6,"parent":0,"code":"gcjg-00012","taskCode":"","ctime":1484196816000}
     * handler : {}
     * nodeCode : 0003
     */

    private String uid;
    private String position;
    private String reallyPosition;
    private SysUserBean sysUser;
    private int taskUserId;
    private String taskType;
    private String nodeName;
    private int pid;
    private String companyName;
    private String taskCode;
    private String companyType;
    private String opinion;
    private int projectTaskId;
    private String projectName;
    private TaskDictionaryBean taskDictionary;
    private HandlerBean handler;
    private String nodeCode;
    private List<ResourceBean> list;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReallyPosition() {
        return reallyPosition;
    }

    public void setReallyPosition(String reallyPosition) {
        this.reallyPosition = reallyPosition;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SysUserBean getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserBean sysUser) {
        this.sysUser = sysUser;
    }

    public int getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(int taskUserId) {
        this.taskUserId = taskUserId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public int getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(int projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public TaskDictionaryBean getTaskDictionary() {
        return taskDictionary;
    }

    public void setTaskDictionary(TaskDictionaryBean taskDictionary) {
        this.taskDictionary = taskDictionary;
    }

    public HandlerBean getHandler() {
        return handler;
    }

    public void setHandler(HandlerBean handler) {
        this.handler = handler;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public List<ResourceBean> getList() {
        return list;
    }

    public void setList(List<ResourceBean> list) {
        this.list = list;
    }

    public static class HandlerBean {
    }


    @Override
    public String toString() {
        return "TaskDetailsBean{" +
                "uid='" + uid + '\'' +
                ", position='" + position + '\'' +
                ", reallyPosition='" + reallyPosition + '\'' +
                ", sysUser=" + sysUser +
                ", taskUserId=" + taskUserId +
                ", taskType='" + taskType + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", pid=" + pid +
                ", companyName='" + companyName + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", companyType='" + companyType + '\'' +
                ", opinion='" + opinion + '\'' +
                ", projectTaskId=" + projectTaskId +
                ", projectName='" + projectName + '\'' +
                ", taskDictionary=" + taskDictionary +
                ", handler=" + handler +
                ", nodeCode='" + nodeCode + '\'' +
                ", list=" + list +
                '}';
    }
}
