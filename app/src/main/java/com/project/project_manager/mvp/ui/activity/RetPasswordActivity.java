package com.project.project_manager.mvp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class RetPasswordActivity extends BaseActivity {

    @BindView(R.id.old_phone)
    EditText oldEdittext;
    @BindView(R.id.new_password)
    EditText newEdittext;
    @BindView(R.id.new_password_two)
    EditText newEdittextTwo;

    private String oldPassword;
    private String newPassword;
    private String newPasswordTwo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ret_password;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("修改密码");
    }

    @OnClick(R.id.btnSure)
    public void savePassword(View view){
        oldPassword = oldEdittext.getText().toString().trim();
        newPassword = newEdittext.getText().toString().trim();
        newPasswordTwo = newEdittextTwo.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword)||TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(newPasswordTwo)){
            UT.show("不能为空");
            return;
        }
        if (!newPassword.equals(newPasswordTwo)){
            UT.show("两次输入不一致");
            return;
        }

        RetrofitManager.getInstance(1).getUpPassObservable(UsrMgr.getUsePhone(),newPassword,oldPassword)
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                         UT.show("原密码错误");
                    }

                    @Override
                    public void onNext(BaseRspObj baseRspObj) {
                      if (baseRspObj.getHead().getRspCode().equals("0")){
                          RetPasswordActivity.this.finish();
                      }
                        UT.show(baseRspObj.getHead().getRspMsg());
                    }
                });
//
    }

}
