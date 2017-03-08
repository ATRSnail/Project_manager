package com.project.project_manager.di.component;

import android.app.Activity;
import android.content.Context;

import com.project.project_manager.di.module.FragmentModule;
import com.project.project_manager.di.scope.ContextLife;
import com.project.project_manager.di.scope.PerFragment;
import com.project.project_manager.mvp.ui.fragment.FillFragment;
import com.project.project_manager.mvp.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(FillFragment fillFragment);

    void inject(HomeFragment homeFragment);
}