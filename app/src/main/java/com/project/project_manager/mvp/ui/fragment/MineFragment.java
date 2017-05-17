package com.project.project_manager.mvp.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.ui.activity.RetPasswordActivity;
import com.project.project_manager.mvp.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/3
 *
 * 我的
 */

public class MineFragment extends BaseFragment{

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews(View view) {
        tv_user_name.setText(UsrMgr.getUsePhone());
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
