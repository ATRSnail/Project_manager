package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class ProjectObjectBean {

    private List<ProjectBean> projectList;

    public List<ProjectBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectBean> projectList) {
        this.projectList = projectList;
    }


    @Override
    public String toString() {
        return "ProjectObjectBean{" +
                "projectList=" + projectList +
                '}';
    }
}
