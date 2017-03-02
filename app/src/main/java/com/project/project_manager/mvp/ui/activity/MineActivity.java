package com.project.project_manager.mvp.ui.activity;

import android.content.Intent;
import android.view.View;

import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;

import butterknife.OnClick;

public class MineActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
      setCustomTitle("我的");
    }

    @OnClick(R.id.tv_password_manage)
    public void onClick(View view){
       startActivity(new Intent(MineActivity.this,RetPasswordActivity.class));
    }
}
