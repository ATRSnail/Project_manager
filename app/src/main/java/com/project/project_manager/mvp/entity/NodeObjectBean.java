package com.project.project_manager.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class NodeObjectBean {

    private List<NodeBean> nodeList;

    public List<NodeBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeBean> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public String toString() {
        return "NodeObjectBean{" +
                "nodeList=" + nodeList +
                '}';
    }
}
