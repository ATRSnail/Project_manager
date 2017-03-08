package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class PvoBean {

    private ProjectCompanyBean projectCompany;

    private List<ResourceBean> list;

    public ProjectCompanyBean getProjectCompany() {
        return projectCompany;
    }

    public void setProjectCompany(ProjectCompanyBean projectCompany) {
        this.projectCompany = projectCompany;
    }

    public List<ResourceBean> getList() {
        return list;
    }

    public void setList(List<ResourceBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PvoBean{" +
                "projectCompany=" + projectCompany +
                ", list=" + list +
                '}';
    }
}
