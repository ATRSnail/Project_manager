package com.project.project_manager.mvp.ui.activity.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.project.project_manager.R;

import butterknife.BindView;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/17
 */

public abstract class BaseRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.news_rv)
    public RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    public ProgressBar mProgressBar;
    public int pageNum = 1;

    public abstract void onRefreshView();

    @Override
    public void initViews() {
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));
    }

    @Override
    public void onRefresh() {
        onRefreshView();
    }
}
