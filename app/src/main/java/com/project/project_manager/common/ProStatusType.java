package com.project.project_manager.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/13
 */

public class ProStatusType {

    public static final String TYPE_NO_COMPLETE = "0"; //未完成
    public static final String TYPE_ALLOCAD = "1"; //已分派
    public static final String TYPE_LOCKED = "2"; //已锁定
    public static final String TYPE_UNAUDITED = "3";//未审核
    public static final String TYPE_REJECT = "4";//驳回
    public static final String TYPE_APPLY = "5";//审核通过

    @StringDef({TYPE_NO_COMPLETE, TYPE_ALLOCAD, TYPE_LOCKED, TYPE_UNAUDITED,TYPE_REJECT,TYPE_APPLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
