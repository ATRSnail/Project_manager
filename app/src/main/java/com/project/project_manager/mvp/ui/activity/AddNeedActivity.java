package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.project_manager.R;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.DictObjBean;
import com.project.project_manager.mvp.entity.ProjectBean;
import com.project.project_manager.mvp.entity.TaskDictionaryBean;
import com.project.project_manager.mvp.entity.TaskDictionaryObjectBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.mvp.ui.adapter.PopuItemAdapter;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.TransformUtils;
import com.project.project_manager.utils.UT;
import com.project.project_manager.weight.RecyclerViewDivider;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;

import static com.project.project_manager.mvp.entity.ProjectBean.PROJECT_KEY;

/**
 * 添加待办
 */
public class AddNeedActivity extends BaseActivity implements View.OnClickListener {

    private static final int PHONE_TAG = 101;
    private static final int VIDEO_TAG = 102;
    private static final String TASK_COMPLETE_TYPE = "task_complete_type";
    private static final String COMPANY_TYPE = "company_type";
    private PopupWindow taskPopupWindow;
    private PopupWindow picPopupWindow;
    private PopuItemAdapter taskRcAdapter;
    private PopuItemAdapter videoRcAdapter;
    private TaskDictionaryBean taskBean;
    private ProjectBean projectBean;
    private String videoType;
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;
    @BindView(R.id.tv_link)
    TextView tv_link;
    @BindView(R.id.tv_video_pic)
    TextView tv_video;
    @BindView(R.id.ll_task)
    LinearLayout ll_task;
    @BindView(R.id.ll_link)
    LinearLayout ll_link;
    @BindView(R.id.btnSure)
    Button btnSure;
    @Inject
    Activity mActivity;

    private List<TaskDictionaryBean> linkIsChecks = new ArrayList<>();
    private List<TaskDictionaryBean> companyIsChecks = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_need;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("添加待办");
        initIntentDate();
        initDataFromUrl();
        initView();
        getDicFromUrl(TASK_COMPLETE_TYPE);
        getDicFromUrl(COMPANY_TYPE);
    }

    private void initIntentDate() {
        projectBean = (ProjectBean)getIntent().getSerializableExtra(PROJECT_KEY);
    }

    private void initPicPop(List<TaskDictionaryBean> dicts) {
        picPopupWindow = initPopWindow(dicts, false);
    }

    private void initView() {
        tv_project_name.setText(projectBean.getProjectName());
        tv_link.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    /**
     * 获取节点
     */
    private void initDataFromUrl() {
        RetrofitManager.getInstance(1).getTaskDicList(UsrMgr.getUseId(), "")
                .compose(TransformUtils.<BaseRspObj<TaskDictionaryObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<TaskDictionaryObjectBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<TaskDictionaryObjectBean> pvoObjectBeanBaseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                        if (pvoObjectBeanBaseRspObj.getHead().getRspCode().equals("0")) {
                            taskPopupWindow = initPopWindow(pvoObjectBeanBaseRspObj.getBody().getTaskDictionary(), true);
                        }
                    }
                });
    }

    /**
     * 添加任务分类
     *
     * @param container
     * @param dicts
     */
    private void setTaskContainer(ViewGroup container, List<TaskDictionaryBean> dicts, boolean isLink) {
        container.removeAllViews();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < dicts.size(); i++) {
            CheckBox checkBox = new CheckBox(container.getContext());
            checkBox.setLayoutParams(layoutParams);
            checkBox.setText(dicts.get(i).getName());
            checkBox.setGravity(Gravity.CENTER_VERTICAL);
            checkBox.setOnCheckedChangeListener(new onCheckChangeListen(dicts.get(i), isLink));
            container.addView(checkBox);
        }

    }

    private void getLinksFromUrl(String id) {
        RetrofitManager.getInstance(1).getTaskDicList(UsrMgr.getUseId(), id)
                .compose(TransformUtils.<BaseRspObj<TaskDictionaryObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<TaskDictionaryObjectBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<TaskDictionaryObjectBean> pvoObjectBeanBaseRspObj) {
                        List<TaskDictionaryBean> dicts = pvoObjectBeanBaseRspObj.getBody().getTaskDictionary();
                        setTaskContainer(ll_link, dicts, true);
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                    }
                });

    }

    private void getDicFromUrl(final String code) {
        RetrofitManager.getInstance(1).getDicDicList(code)
                .compose(TransformUtils.<BaseRspObj<DictObjBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<DictObjBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<DictObjBean> pvoObjectBeanBaseRspObj) {
                        List<TaskDictionaryBean> dicts = pvoObjectBeanBaseRspObj.getBody().getDicts();
                        if (code.equals(COMPANY_TYPE)) {
                            setTaskContainer(ll_task, dicts, false);
                        } else {
                            initPicPop(dicts);
                        }
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                    }
                });

    }

    /**
     * 添加待办
     */
    private void addTaskDis() {
        if (linkIsChecks == null || linkIsChecks.isEmpty()) {
            UT.show("环节或节点未选");
            return;
        }
        if (companyIsChecks == null || companyIsChecks.isEmpty()) {
            UT.show("任务分类未选");
            return;
        }
        if (TextUtils.isEmpty(videoType)) {
            UT.show("要求未选");
            return;
        }
        String codes = listToString(linkIsChecks, true);
        String names = listToString(linkIsChecks, false);
        String companyType = listToString(companyIsChecks, true);
        RetrofitManager.getInstance(1).addTaskDis(codes, names, videoType, projectBean.getId()+"", companyType)
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj pvoObjectBeanBaseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                        UT.show(pvoObjectBeanBaseRspObj.getHead().getRspMsg());
                        if (pvoObjectBeanBaseRspObj.getHead().getRspCode().equals("0")) {
                            finish();
                        }
                    }
                });

    }

    public static String listToString(List<TaskDictionaryBean> list, boolean isCode) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (TaskDictionaryBean string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(isCode ? string.getCode() : string.getName());
        }
        return result.toString();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_link:
                showPopu(taskPopupWindow, view);
                break;
            case R.id.tv_video_pic:
                showPopu(picPopupWindow, view);
                break;
            case R.id.btnSure:
                addTaskDis();
                break;
        }
    }

    /**
     * 显示pop
     *
     * @param pop
     * @param view
     */
    private void showPopu(PopupWindow pop, View view) {
        if (pop != null && !pop.isShowing()) {
            pop.showAtLocation(view, Gravity.CENTER, 0, 0);
            setBackgroundAlpha(0.5f);//设置屏幕透明度
        }
    }

    /**
     * 隐藏pop
     *
     * @param pop
     */
    private void dissPopu(PopupWindow pop) {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            setBackgroundAlpha(1.0f);//设置屏幕透明度
        }
    }

    private PopuItemAdapter initPopAdapter(List<TaskDictionaryBean> taskDictionary, boolean isTaskPup) {
        PopuItemAdapter adapter = null;
        if (isTaskPup) {
            taskRcAdapter = new PopuItemAdapter(R.layout.item_pop_text, taskDictionary);
        } else {
            videoRcAdapter = new PopuItemAdapter(R.layout.item_pop_text, taskDictionary);
        }
        if (videoRcAdapter != null) {
            videoRcAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    dissPopu(picPopupWindow);
                    taskBean = videoRcAdapter.getItem(i);
                    tv_video.setText(taskBean.getName());
                    videoType = taskBean.getCode();
                }
            });
        }
        if (taskRcAdapter != null) {
            taskRcAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    dissPopu(taskPopupWindow);
                    taskBean = taskRcAdapter.getItem(i);
                    tv_link.setText(taskBean.getName());
                    getLinksFromUrl(taskBean.getId() + "");
                }
            });
        }
        adapter = isTaskPup ? taskRcAdapter : videoRcAdapter;
        return adapter;
    }

    /**
     * 初始化popu
     *
     * @param taskDictionary
     * @param isTaskPup      是否是节点pop
     * @return
     */
    private PopupWindow initPopWindow(List<TaskDictionaryBean> taskDictionary, boolean isTaskPup) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.listview_demo, null);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.listview);

        listView.setHasFixedSize(true);
        listView.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        listView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(initPopAdapter(taskDictionary, isTaskPup));
        //自适配长、框设置
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_circle_5));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
        return popupWindow;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
    }

    class onCheckChangeListen implements CompoundButton.OnCheckedChangeListener {
        TaskDictionaryBean ta;
        boolean isLink;

        public onCheckChangeListen(TaskDictionaryBean ta, boolean isLink) {
            this.ta = ta;
            this.isLink = isLink;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isLink) {
                if (isChecked) {
                    linkIsChecks.add(ta);
                } else {
                    linkIsChecks.remove(ta);
                }
            } else {
                if (isChecked) {
                    companyIsChecks.add(ta);
                } else {
                    companyIsChecks.remove(ta);
                }
            }
        }
    }

}
