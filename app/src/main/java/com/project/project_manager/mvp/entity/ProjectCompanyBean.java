package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class ProjectCompanyBean {


    /**
     * uid : 1
     * id : 50
     * utime : 1484272843000
     * sysUser : {"niceName":"超级管理员","utime":1488185237000,"imei":"111","state":"1","userEmail":"321@qq.com","ctime":1467078959000,"passWord":"06610606bfae38f152674dcb63c56395","phoneModel":"11","userType":"","id":"1","phoneNo":"18500241615","systemeType":"2","userName":"admin","createUserId":""}
     * companyType : 0
     * pid : 1
     * companyName : 汉铭
     * ctime : 1484272843000
     * companyCode : 0001
     */

    private String uid;
    private int id;
    private long utime;
    private SysUserBean sysUser;
    private String companyType;
    private int pid;
    private String companyName;
    private long ctime;
    private String companyCode;

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

    public SysUserBean getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserBean sysUser) {
        this.sysUser = sysUser;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public String toString() {
        return "ProjectCompanyBean{" +
                "uid='" + uid + '\'' +
                ", id=" + id +
                ", utime=" + utime +
                ", sysUser=" + sysUser +
                ", companyType='" + companyType + '\'' +
                ", pid=" + pid +
                ", companyName='" + companyName + '\'' +
                ", ctime=" + ctime +
                ", companyCode='" + companyCode + '\'' +
                '}';
    }
}
