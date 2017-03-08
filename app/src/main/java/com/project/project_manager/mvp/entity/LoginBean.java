package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class LoginBean {

    /**
     * phoneNo : 13521884420
     * niceName : 陈金诚
     * id : sDmpexZH4c0rkISQldnckA9jJxTzOdbr
     * utime : 1488349320000
     * systemeType : 1
     * imei : 123456
     * state : 1
     * userEmail : 123@qq.com
     * ctime : 1488349276000
     * phoneModel : 123456
     * createUserId : 1
     * userType : 0
     */

    private String phoneNo;
    private String niceName;
    private String id;
    private long utime;
    private String systemeType;
    private String imei;
    private String state;
    private String userEmail;
    private long ctime;
    private String phoneModel;
    private String createUserId;
    private String userType;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getSystemeType() {
        return systemeType;
    }

    public void setSystemeType(String systemeType) {
        this.systemeType = systemeType;
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

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "phoneNo='" + phoneNo + '\'' +
                ", niceName='" + niceName + '\'' +
                ", id='" + id + '\'' +
                ", utime=" + utime +
                ", systemeType='" + systemeType + '\'' +
                ", imei='" + imei + '\'' +
                ", state='" + state + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", ctime=" + ctime +
                ", phoneModel='" + phoneModel + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
