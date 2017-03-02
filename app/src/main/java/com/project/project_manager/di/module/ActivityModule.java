package com.project.project_manager.di.module;

import android.app.Activity;
import android.content.Context;

import com.project.project_manager.di.scope.ContextLife;
import com.project.project_manager.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context ProvideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity ProvideActivity() {
        return mActivity;
    }
}