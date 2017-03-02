package com.project.project_manager.utils;

import android.content.Context;
import android.content.res.Resources;

import com.project.project_manager.App;
import com.project.project_manager.R;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class MyUtils {

    public static void cancelSubscription(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static String analyzeNetworkError(Throwable e) {
        String errMsg = App.getAppContext().getString(R.string.load_error);
        if (e instanceof HttpException) {
            int state = ((HttpException) e).code();
            if (state == 403) {
                errMsg = App.getAppContext().getString(R.string.retry_after);
            }
        }
        return errMsg;
    }

    public static int dp2px(Context paramContext, float paramFloat) {
        return dp2px(paramContext.getResources(), paramFloat);
    }

    public static int dp2px(Resources paramResources, float paramFloat) {
        return (int) (0.5F + paramFloat * paramResources.getDisplayMetrics().density);
    }

}
