package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.mvp.ui.adapter.FileAdapter;
import com.project.project_manager.mvp.entity.FileBean;
import com.project.project_manager.weight.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener {

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
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.news_rv)
    RecyclerView mRecycleView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    Activity mActivity;
    private FileAdapter mFileAdapter;
    private List<FileBean> fileBeen;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mProgressBar.setVisibility(View.GONE);
                mFileAdapter.setNewData(fileBeen);
            }
        }
    };

    @Override
    public void initViews() {

        initTopView();
        initRecycleView();
        initData();
    }

    private void initTopView() {
        mSwipeRefreshLayout.setEnabled(false);
        mToolbar.setNavigationIcon(null);
        mToolbar.setNavigationOnClickListener(null);
        setCustomTitle("首页");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TITLE.length; i++) {
                    fileBeen.add(new FileBean(TITLE[i], IMG[i], CLASS[i]));
                }
                mHandler.sendEmptyMessage(100);
            }
        }).start();
    }

    private void initRecycleView() {
        mRecycleView.setHasFixedSize(true);
        mRecycleView.addItemDecoration(new MarginDecoration(mActivity));
        mRecycleView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        fileBeen = new ArrayList<>();
        mFileAdapter = new FileAdapter(R.layout.item_file, fileBeen);
        mFileAdapter.setOnRecyclerViewItemClickListener(this);
        mRecycleView.setAdapter(mFileAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        mFileAdapter.getData().get(i).intentToAct(mActivity);
    }
}
