package com.project.project_manager.mvp.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.RetPasswordActivity;
import com.project.project_manager.mvp.ui.fragment.base.BaseFragment;

import butterknife.OnClick;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/3
 *
 * 我的
 */

public class MineFragment extends BaseFragment{

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews(View view) {

    }

    @OnClick(R.id.tv_password_manage)
    public void onClick(View view){
        startActivity(new Intent(getContext(),RetPasswordActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
