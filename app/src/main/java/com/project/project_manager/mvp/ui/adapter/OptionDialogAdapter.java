package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.OptionBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/6
 */

public class OptionDialogAdapter extends BaseQuickAdapter<OptionBean>{
    public OptionDialogAdapter(int layoutResId, List<OptionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OptionBean optionBean) {
        baseViewHolder.setText(R.id.tv_project_name,optionBean.getName());
    }
}
