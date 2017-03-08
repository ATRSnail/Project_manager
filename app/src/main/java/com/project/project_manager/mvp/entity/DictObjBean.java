package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/8
 */

public class DictObjBean {
    private List<TaskDictionaryBean> dicts;

    public List<TaskDictionaryBean> getDicts() {
        return dicts;
    }

    public void setDicts(List<TaskDictionaryBean> dicts) {
        this.dicts = dicts;
    }

    @Override
    public String toString() {
        return "DictObjBean{" +
                "dicts=" + dicts +
                '}';
    }
}
