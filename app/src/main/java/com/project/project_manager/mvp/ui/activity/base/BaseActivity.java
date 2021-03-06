package com.project.project_manager.mvp.ui.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.project_manager.App;
import com.project.project_manager.R;
import com.project.project_manager.di.component.ActivityComponent;
import com.project.project_manager.di.component.DaggerActivityComponent;
import com.project.project_manager.di.module.ActivityModule;
import com.project.project_manager.mvp.presenter.base.BasePresenter;
import com.project.project_manager.utils.NetUtil;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {


    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.btnRight)
    Button mToolbarRightBtn;


    protected T mPresenter;

    protected ActivityComponent mActivityComponent;

    public abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void initViews();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i(getClass().getSimpleName());
        NetUtil.isNetworkErrThenShowMsg();
        initActivityComponent();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        ButterKnife.bind(this);
        initViews();

        if (mPresenter != null){
            mPresenter.onCreate();
        }

        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    public void setCustomTitle(String title) {
        if (mToolbarTitle != null)
            mToolbarTitle.setText(title);
    }

    public void setRightBtnClick(View.OnClickListener btnClick){
        if (mToolbarRightBtn != null) {
            mToolbarRightBtn.setVisibility(View.VISIBLE);
            mToolbarRightBtn.setOnClickListener(btnClick);
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null){
            mPresenter.onDestory();
        }
    }

    public SweetAlertDialog initDialog(String content){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(content);
        pDialog.setCancelable(false);
        return pDialog;
    }

    public String setCate(String companyType) {
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

    //任务完成方式(字典)-0拍照,1拍照＋录像,2录像
    public String setDemand(String taskType) {
        if (taskType.equals("0")) {
            return "拍照";
        }
        if (taskType.equals("1")) {
            return "拍照＋录像";
        }
        if (taskType.equals("2")) {
            return "录像";
        }
        return "";
    }

}
