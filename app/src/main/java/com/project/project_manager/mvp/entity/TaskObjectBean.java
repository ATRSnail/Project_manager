package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/2
 */

public class TaskObjectBean {

    private List<TaskBean> taskList;

    public List<TaskBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskBean> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "TaskObjectBean{" +
                "taskList=" + taskList +
                '}';
    }
}
