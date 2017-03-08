package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class ResourceBean {


    /**
     * id : 17
     * utime : null
     * projectTaskId : 242
     * resourceUrl : image201701191725061683.jpg
     * ctime : 1484817909633
     * resourceType : 1
     */

    private int id;
    private Object utime;
    private int projectTaskId;
    private String resourceUrl;
    private long ctime;
    private String resourceType;

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

    public int getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(int projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return "ResourceBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", projectTaskId=" + projectTaskId +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", ctime=" + ctime +
                ", resourceType='" + resourceType + '\'' +
                '}';
    }
}
