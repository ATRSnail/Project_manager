package com.project.project_manager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.FileBean;
import com.project.project_manager.mvp.ui.activity.ConstructionActivity;
import com.project.project_manager.mvp.ui.activity.FillListActivity;
import com.project.project_manager.mvp.ui.activity.MineActivity;
import com.project.project_manager.mvp.ui.activity.NeedToDoActivity;
import com.project.project_manager.mvp.ui.adapter.FileAdapter;
import com.project.project_manager.mvp.ui.fragment.base.BaseRefreshFragment;
import com.project.project_manager.weight.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/3
 */

public class HomeFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener,View.OnClickListener {

    private static final String[] TITLE = new String[]{"归档"
            , "待办"
            , "视频电话会议"
            , "回放"
            , "通讯录"
            , "对讲"
            , "我的"};
    private static final int[] IMG = new int[]{R.mipmap.page_home_iocn_archive,
            R.mipmap.page_home_icon_upcoming,
            R.mipmap.page_home_icon_video_teleconference,
            R.mipmap.page_home_icon_playback,
            R.mipmap.page_home_icon_contacts,
            R.mipmap.page_home_icon_intercom,
            R.mipmap.page_home_icon_mine};
    private static final Class[] CLASS = new Class[]{FillListActivity.class
            , NeedToDoActivity.class
            , ConstructionActivity.class
            , NeedToDoActivity.class
            , NeedToDoActivity.class
            , NeedToDoActivity.class
            , MineActivity.class};

    @Inject
    Activity mActivity;
    private FileAdapter mFileAdapter;
    private List<FileBean> fileBeen;
    private View mTitleHeader;
    private View mCategyView;

    @Override
    public void onRefreshView() {

    }

    @Override
    public void initViews(View view) {

        initHeadView();
        clickEvent();
        initRecycleView();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    private void initHeadView() {
        mCategyView = View.inflate(mActivity, R.layout.item_head_categy, null);
        mTitleHeader = View.inflate(mActivity, R.layout.layout_head_text, null);
    }


    private void initRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fileBeen = new ArrayList<>();
        mFileAdapter = new FileAdapter(R.layout.item_file, fileBeen);
        mFileAdapter.addHeaderView(mCategyView);
        mFileAdapter.addHeaderView(mTitleHeader);
        mFileAdapter.setOnRecyclerViewItemClickListener(this);
        mRecyclerView.setAdapter(mFileAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        mFileAdapter.getData().get(i).intentToAct(mActivity);
    }

    public void clickEvent() {
        mCategyView.findViewById(R.id.ll_fill).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_meeting).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_talk).setOnClickListener(this);
        mCategyView.findViewById(R.id.ll_repate).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fill:
                startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_meeting:
                startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_talk:
                startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
            case R.id.ll_repate:
                startActivity(new Intent(mActivity, NeedToDoActivity.class));
                break;
        }
    }
}
