package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class LinkObjectBean {

    private List<LinkBean> linkList;

    public List<LinkBean> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<LinkBean> linkList) {
        this.linkList = linkList;
    }

    @Override
    public String toString() {
        return "LinkObjectBean{" +
                "linkList=" + linkList +
                '}';
    }
}
