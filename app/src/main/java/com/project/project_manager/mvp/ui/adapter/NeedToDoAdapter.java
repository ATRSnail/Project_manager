package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.BaseNewBean;
import com.project.project_manager.utils.DateUtil;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */

public class NeedToDoAdapter extends BaseQuickAdapter<BaseNewBean>{
    public NeedToDoAdapter(int layoutResId, List<BaseNewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BaseNewBean baseNewBean) {

        baseViewHolder.setText(R.id.tv_project_name,baseNewBean.getTitle());
        baseViewHolder.setText(R.id.tv_date, DateUtil.getCurGroupDay(baseNewBean.getCtime()));
    }
}
