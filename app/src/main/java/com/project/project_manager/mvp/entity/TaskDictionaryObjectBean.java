package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class TaskDictionaryObjectBean {
    private List<TaskDictionaryBean> taskDictionary;

    public List<TaskDictionaryBean> getTaskDictionary() {
        return taskDictionary;
    }

    public void setTaskDictionary(List<TaskDictionaryBean> taskDictionary) {
        this.taskDictionary = taskDictionary;
    }

    @Override
    public String toString() {
        return "TaskDictionaryObjectBean{" +
                "taskDictionary=" + taskDictionary +
                '}';
    }
}
