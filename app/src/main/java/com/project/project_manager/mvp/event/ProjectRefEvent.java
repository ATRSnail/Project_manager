package com.project.project_manager.mvp.event;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class ProjectRefEvent {

    private int pos = -1;
    public ProjectRefEvent(int pos){
        this.pos = pos;
    }

    public ProjectRefEvent(){

    }

    public int getPos() {
        return pos;
    }
}
