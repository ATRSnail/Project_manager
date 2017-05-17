package com.project.project_manager.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.TaskBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscriber;

import static com.project.project_manager.common.ProStatusType.TYPE_ALLOCAD;
import static com.project.project_manager.common.ProStatusType.TYPE_APPLY;
import static com.project.project_manager.common.ProStatusType.TYPE_LOCKED;
import static com.project.project_manager.common.ProStatusType.TYPE_NO_COMPLETE;
import static com.project.project_manager.common.ProStatusType.TYPE_REJECT;
import static com.project.project_manager.common.ProStatusType.TYPE_UNAUDITED;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/18
 * 某个项目施工列表
 */

public class ProjectListAdapter extends BaseQuickAdapter<TaskBean> {
    public ProjectListAdapter(int layoutResId, List<TaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final TaskBean baseNewBean) {
        baseViewHolder.setText(R.id.tv_cate, setCate(baseNewBean.getCompanyType()));
        baseViewHolder.setText(R.id.tv_title, baseNewBean.getNodeName());
        baseViewHolder.setText(R.id.tv_content, baseNewBean.getTaskDictionary().getName());
        baseViewHolder.setText(R.id.tv_verify_btn, setStatue(baseNewBean.getStatus()));
        TextView tv_lock_btn = baseViewHolder.getView(R.id.tv_lock_btn);
        if (baseNewBean.getStatus().equals(TYPE_NO_COMPLETE)) {
            tv_lock_btn.setVisibility(View.VISIBLE);
        } else {
            tv_lock_btn.setVisibility(View.GONE);
        }
        tv_lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.getInstance(1).getAuditStatus(baseNewBean.getTaskUserId() + "", TYPE_LOCKED, "", baseNewBean.getProjectTaskId() + "")
                        .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                        .subscribe(new Subscriber<BaseRspObj>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseRspObj baseRspObj) {
                                refStatus(baseNewBean,TYPE_LOCKED);
                                KLog.a("pvoObjectBeanBaseRspObj---" + baseRspObj.toString());
                                UT.show(baseRspObj.getHead().getRspMsg());
                            }
                        });
            }
        });
    }

    public void refStatus(TaskBean baseNewBean,String status){
        baseNewBean.setStatus(TYPE_LOCKED);
        notifyDataSetChanged();
    }

    private String setCate(String companyType) {
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

//    人物任务状态(字典)-0未完成,1已分派,2已锁定,3未审核,4驳回,5审核通过

    private String setStatue(String status) {
        if (status.equals(TYPE_NO_COMPLETE)) {
            return "未完成";
        }
        if (status.equals(TYPE_ALLOCAD)) {
            return "已分派";
        }
        if (status.equals(TYPE_LOCKED)) {
            return "已锁定";
        }
        if (status.equals(TYPE_UNAUDITED)) {
            return "未审核";
        }
        if (status.equals(TYPE_REJECT)) {
            return "驳回";
        }
        if (status.equals(TYPE_APPLY)) {
            return "审核通过";
        }
        return "";
    }
}
