package com.project.project_manager.mvp.ui.activity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.container.GridViewForScrollView;
import com.jaiky.imagespickers.container.SimpleImageAdapter;
import com.jaiky.imagespickers.utils.GlideLoader;
import com.jaiky.imagespickers.utils.Utils;
import com.project.project_manager.R;
import com.project.project_manager.mvp.entity.NodeBean;
import com.project.project_manager.mvp.entity.PvoBean;
import com.project.project_manager.mvp.entity.PvoObjectBean;
import com.project.project_manager.mvp.entity.ResourceBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.repository.RetrofitManager;
import com.project.project_manager.utils.ScreenUtils;
import com.project.project_manager.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Subscriber;

import static com.project.project_manager.common.ApiConstants.IMG_HOST;
import static com.project.project_manager.mvp.entity.NodeBean.NODE_KEY;

/**
 * 施工方
 */
public class ConstructionActivity extends BaseActivity {
    private ArrayList<String> imgPathList = new ArrayList<>();
    private ArrayList<String> vidPathList = new ArrayList<>();

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayerStandard;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    private NodeBean nodeBean;
    private PvoBean pvoBean;
    @Inject
    Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_construction;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("施工方");
        setContainer(llContainer, 3, false);
        initIntentDate();
        postDataFroUrl();
    }

    private void initIntentDate() {
        nodeBean = (NodeBean) getIntent().getSerializableExtra(NODE_KEY);
    }

    private void postDataFroUrl() {
        RetrofitManager.getInstance(1).getProjectPvoObservable(nodeBean.getId() + "")
                .compose(TransformUtils.<BaseRspObj<PvoObjectBean>>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj<PvoObjectBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj<PvoObjectBean> pvoObjectBeanBaseRspObj) {
                        KLog.a("pvoObjectBeanBaseRspObj---" + pvoObjectBeanBaseRspObj.toString());
                        if (pvoObjectBeanBaseRspObj.getHead().getRspCode().equals("0")) {
                            pvoBean = pvoObjectBeanBaseRspObj.getBody().getPvo();
                            List<ResourceBean> resources = pvoBean.getList();
                            if (resources.size() > 0) {
                                for (ResourceBean res : resources) {
                                    if (res.getResourceType().equals("0")) {
                                        imgPathList.add(IMG_HOST + res.getResourceUrl());
                                    } else if (res.getResourceType().equals("1")) {
                                        vidPathList.add(IMG_HOST + res.getResourceUrl());
                                    }
                                }
                            }
                            containerAdapter.refreshData(imgPathList, new GlideLoader());
                            initVideoView(vidPathList);
                        }
                    }
                });
    }

    /**
     * 初始化videoView
     *
     * @param pathList
     */
    private void initVideoView(List<String> pathList) {
        if (pathList.size() <= 0) {
            mJCVideoPlayerStandard.setVisibility(View.GONE);
            return;
        }
        mJCVideoPlayerStandard.setUp(
                pathList.get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "aa");

        Glide.with(mActivity)
                .load("http://gb.cri.cn/mmsource/images/2010/07/19/ec100719005.jpg")
                .into(mJCVideoPlayerStandard.thumbImageView);
    }

    private SimpleImageAdapter containerAdapter;

    public void setContainer(ViewGroup container, int rowImageCount, boolean isDelete) {
        if (container.getChildCount() == 0) {
            //新建一个GridView
            GridViewForScrollView gvView = new GridViewForScrollView(container.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            gvView.setLayoutParams(layoutParams);

            if (isDelete) {
                //间距3dp
                gvView.setHorizontalSpacing(Utils.dip2px(container.getContext(), 3));
                gvView.setVerticalSpacing(Utils.dip2px(container.getContext(), 3));
            } else {
                //间距10dp
                gvView.setHorizontalSpacing(Utils.dip2px(container.getContext(), 10));
                gvView.setVerticalSpacing(Utils.dip2px(container.getContext(), 10));
            }
            gvView.setNumColumns(rowImageCount);
            gvView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

            //设置适配器，暂时数据为控
            containerAdapter = new SimpleImageAdapter(container, isDelete, rowImageCount, ScreenUtils.getScreenWidth(mActivity));
            gvView.setAdapter(containerAdapter);
            container.addView(gvView);
        } else {
            GridViewForScrollView gvView = (GridViewForScrollView) container.getChildAt(0);
            containerAdapter = (SimpleImageAdapter) gvView.getAdapter();
        }
    }

}
