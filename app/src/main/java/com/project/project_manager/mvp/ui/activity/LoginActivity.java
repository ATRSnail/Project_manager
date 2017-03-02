package com.project.project_manager.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.response.RspLoginBean;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    EditText phoneEt;
    @BindView(R.id.code)
    EditText passwordEt;
    private String phoneNum;
    private String password;
    private SweetAlertDialog pDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        initDialog();
        phoneEt.setText("18500241615");
        passwordEt.setText("a123456");
    }

    private void initDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }

    @OnClick(R.id.btnSure)
    public void onLogin(View view) {
//
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        LoginActivity.this.finish();
//

        phoneNum = phoneEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
            UT.show("不能为空");
            return;
        }
        pDialog.show();
        KLog.d("ddddd");
        RetrofitManager.getInstance(1).getLoginObservable(phoneNum, password)
                .compose(TransformUtils.<RspLoginBean>defaultSchedulers())
                .subscribe(new Subscriber<RspLoginBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        pDialog.dismiss();
                        UT.show(e.getMessage());
                    }

                    @Override
                    public void onNext(RspLoginBean rspUserBean) {
                        pDialog.dismiss();
                        KLog.a("user--->" + rspUserBean.toString());
                        UT.show(rspUserBean.getHead().getRspMsg());
                        if (rspUserBean.getHead().getRspCode().equals("0")) {
                            UsrMgr.cacheUserInfo(new Gson().toJson(rspUserBean.getBody().getUser()));
                            KLog.a("userInfo--->" + UsrMgr.getUseInfo().toString());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();

                        }

                    }
                });

    }
}
