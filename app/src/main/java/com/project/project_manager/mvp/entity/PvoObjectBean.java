package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/6
 */

public class PvoObjectBean {

    private PvoBean pvo;

    public PvoBean getPvo() {
        return pvo;
    }

    public void setPvo(PvoBean pvo) {
        this.pvo = pvo;
    }

    @Override
    public String toString() {
        return "PvoObjectBean{" +
                "pvo=" + pvo +
                '}';
    }
}
