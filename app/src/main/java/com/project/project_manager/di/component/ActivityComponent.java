package com.project.project_manager.di.component;

import android.app.Activity;
import android.content.Context;

import com.project.project_manager.mvp.ui.activity.AddNeedActivity;
import com.project.project_manager.mvp.ui.activity.ConstructionActivity;
import com.project.project_manager.mvp.ui.activity.FillListActivity;
import com.project.project_manager.mvp.ui.activity.LinkActivity;
import com.project.project_manager.mvp.ui.activity.MainActivity;
import com.project.project_manager.mvp.ui.activity.NeedToDoActivity;
import com.project.project_manager.mvp.ui.activity.NodesActivity;
import com.project.project_manager.mvp.ui.activity.ProjectItemsActivity;
import com.project.project_manager.di.module.ActivityModule;
import com.project.project_manager.di.scope.ContextLife;
import com.project.project_manager.di.scope.PerActivity;
import com.project.project_manager.mvp.ui.activity.RecordActivity;
import com.project.project_manager.mvp.ui.activity.VerifyDetailActivity;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(NeedToDoActivity needToDoActivity);

    void inject(ProjectItemsActivity projectItemsActivity);

    void inject(VerifyDetailActivity verifyDetailActivity);

    void inject(FillListActivity fillListActivity);

    void inject(LinkActivity linkActivity);

    void inject(NodesActivity nodesActivity);

    void inject(ConstructionActivity constructionActivity);

    void inject(RecordActivity recordActivity);

    void inject(AddNeedActivity addNeedActivity);
}
