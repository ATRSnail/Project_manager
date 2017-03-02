package com.project.project_manager.di.component;

import android.content.Context;

import com.project.project_manager.di.module.ApplicationModule;
import com.project.project_manager.di.scope.ContextLife;
import com.project.project_manager.di.scope.PerApp;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}