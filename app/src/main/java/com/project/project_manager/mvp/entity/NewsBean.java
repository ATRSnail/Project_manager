package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class NewsBean {
    private List<BaseNewBean> newsList;

    public List<BaseNewBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<BaseNewBean> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "newsList=" + newsList +
                '}';
    }
}
