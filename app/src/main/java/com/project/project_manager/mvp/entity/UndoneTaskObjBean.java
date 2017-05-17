package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/10
 */

public class UndoneTaskObjBean {
    private List<TaskBean> undoneTask;

    public List<TaskBean> getUndoneTask() {
        return undoneTask;
    }

    public void setUndoneTask(List<TaskBean> undoneTask) {
        this.undoneTask = undoneTask;
    }

    @Override
    public String toString() {
        return "UndoneTaskObjBean{" +
                "undoneTask=" + undoneTask +
                '}';
    }
}
