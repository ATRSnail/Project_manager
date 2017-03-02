package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.LinkBean;
import com.project.project_manager.utils.DateUtil;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */

public class LinksDoAdapter extends BaseQuickAdapter<LinkBean>{
    public LinksDoAdapter(int layoutResId, List<LinkBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LinkBean baseNewBean) {

        baseViewHolder.setText(R.id.tv_project_name,baseNewBean.getNodeName());
        baseViewHolder.setText(R.id.tv_date, DateUtil.getCurGroupDay(baseNewBean.getCtime()));
    }
}
