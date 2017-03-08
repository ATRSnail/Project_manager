package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class SysUserBean {


    /**
     * niceName : 超级管理员
     * utime : 1488185237000
     * imei : 111
     * state : 1
     * userEmail : 321@qq.com
     * ctime : 1467078959000
     * passWord : 06610606bfae38f152674dcb63c56395
     * phoneModel : 11
     * userType :
     * id : 1
     * phoneNo : 18500241615
     * systemeType : 2
     * userName : admin
     * createUserId :
     */

    private String niceName;
    private long utime;
    private String imei;
    private String state;
    private String userEmail;
    private long ctime;
    private String passWord;
    private String phoneModel;
    private String userType;
    private String id;
    private String phoneNo;
    private String systemeType;
    private String userName;
    private String createUserId;

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSystemeType() {
        return systemeType;
    }

    public void setSystemeType(String systemeType) {
        this.systemeType = systemeType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return "SysUserBean{" +
                "niceName='" + niceName + '\'' +
                ", utime=" + utime +
                ", imei='" + imei + '\'' +
                ", state='" + state + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", ctime=" + ctime +
                ", passWord='" + passWord + '\'' +
                ", phoneModel='" + phoneModel + '\'' +
                ", userType='" + userType + '\'' +
                ", id='" + id + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", systemeType='" + systemeType + '\'' +
                ", userName='" + userName + '\'' +
                ", createUserId='" + createUserId + '\'' +
                '}';
    }
}
