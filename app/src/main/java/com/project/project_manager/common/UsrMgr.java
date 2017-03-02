package com.project.project_manager.common;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.project.project_manager.App;
import com.project.project_manager.mvp.entity.LoginBean;
import com.project.project_manager.utils.SharedPrefsUtils;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class UsrMgr {

    public static final String USER_INFO = "userinfo";
    private static Context context = App.getAppContext();

    /**
     * 获取用户信息
     *
     * @return
     */
    public static LoginBean getUseInfo() {
        LoginBean model = null;
        String string = SharedPrefsUtils.getStringPreference(context, USER_INFO);
        if (!TextUtils.isEmpty(string)) {
            model = new Gson().fromJson(string, LoginBean.class);
        }
        return model;
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUseId() {
        LoginBean model = getUseInfo();
        return model != null ? model.getId() : "";
    }

    /**
     * 获取用户电话
     *
     * @return
     */
    public static String getUsePhone() {
        LoginBean model = getUseInfo();
        return model != null ? model.getPhoneNo() : "";
    }

    /**
     * 缓存用户信息
     *
     * @param json
     */
    public static void cacheUserInfo(String json) {
        SharedPrefsUtils.setStringPreference(context, USER_INFO, json);
    }

    /**
     * 清除缓存数据
     */
    public static void clearUserInfo() {
        cacheUserInfo("");
    }

    /**
     * 判断是否已登录
     *
     * @return
     */
    public static boolean isLogin() {
        LoginBean model = getUseInfo();
        if (model != null) {
            return true;
        } else {
            return false;
        }
    }
}
