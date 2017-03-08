package com.project.project_manager.mvp.entity;

import com.project.project_manager.mvp.TaskDetailsBean;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/8
 */

public class TaskDetailsObjBean {
    private TaskDetailsBean taskDetails;

    public TaskDetailsBean getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(TaskDetailsBean taskDetails) {
        this.taskDetails = taskDetails;
    }

    @Override
    public String toString() {
        return "TaskDetailsObjBean{" +
                "taskDetails=" + taskDetails +
                '}';
    }
}
