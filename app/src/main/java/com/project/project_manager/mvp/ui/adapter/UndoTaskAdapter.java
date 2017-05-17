package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.TaskBean;

import java.util.List;

import static com.project.project_manager.common.ProStatusType.TYPE_ALLOCAD;
import static com.project.project_manager.common.ProStatusType.TYPE_APPLY;
import static com.project.project_manager.common.ProStatusType.TYPE_LOCKED;
import static com.project.project_manager.common.ProStatusType.TYPE_NO_COMPLETE;
import static com.project.project_manager.common.ProStatusType.TYPE_REJECT;
import static com.project.project_manager.common.ProStatusType.TYPE_UNAUDITED;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */

public class UndoTaskAdapter extends BaseQuickAdapter<TaskBean>{
    public UndoTaskAdapter(int layoutResId, List<TaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TaskBean fileBean) {
        baseViewHolder.setText(R.id.tv_people,setCate(fileBean.getCompanyType()));
        baseViewHolder.setText(R.id.tv_name,fileBean.getNodeName());
        baseViewHolder.setText(R.id.tv_content,fileBean.getTaskDictionary().getName());
        baseViewHolder.setText(R.id.tv_status,setStatue(fileBean.getStatus()));
    }

    private String setCate(String companyType) {
        if (companyType.equals("0")) {
            return "监管方";
        }
        if (companyType.equals("1")) {
            return "设计方";
        }
        if (companyType.equals("2")) {
            return "施工方";
        }
        return "";
    }

    //    人物任务状态(字典)-0未完成,1已分派,2已锁定,3未审核,4驳回,5审核通过

    private String setStatue(String status) {
        if (status.equals(TYPE_NO_COMPLETE)) {
            return "未完成";
        }
        if (status.equals(TYPE_ALLOCAD)) {
            return "已分派";
        }
        if (status.equals(TYPE_LOCKED)) {
            return "已锁定";
        }
        if (status.equals(TYPE_UNAUDITED)) {
            return "未审核";
        }
        if (status.equals(TYPE_REJECT)) {
            return "驳回";
        }
        if (status.equals(TYPE_APPLY)) {
            return "审核通过";
        }
        return "";
    }
}
