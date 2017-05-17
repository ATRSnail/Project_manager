package com.project.project_manager.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.FileBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */
@Deprecated
public class FileAdapter extends BaseQuickAdapter<FileBean>{
    public FileAdapter(int layoutResId, List<FileBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, FileBean fileBean) {
        baseViewHolder.setText(R.id.tv_name,fileBean.getName());
        baseViewHolder.setImageResource(R.id.img_cate,fileBean.getImgSrc());

    }
}
