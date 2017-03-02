package com.project.project_manager.repository;

import android.text.TextUtils;
import android.util.SparseArray;

import com.project.project_manager.App;
import com.project.project_manager.common.ApiConstants;
import com.project.project_manager.common.Constants;
import com.project.project_manager.mvp.entity.LinkObjectBean;
import com.project.project_manager.mvp.entity.NodeObjectBean;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.response.RspLoginBean;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.utils.NetUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class RetrofitManager {

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    private NewsService mNewsService;

    private static SparseArray<RetrofitManager> sRetrofitManager = new SparseArray<>();
    private static volatile OkHttpClient sOkHttpClient;

    public RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost())
                .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mNewsService = retrofit.create(NewsService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                KLog.d("no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            KLog.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            KLog.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

    public Observable<RspNewsBean> getNewsListObservable(int pageNum, String title) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("numPerPage", Constants.numPerPage + "");
        if (!TextUtils.isEmpty(title))
            map.put("title", title);
        KLog.a(map.toString());
        return mNewsService.getNewsList(map);
    }

    public Observable<RspLoginBean> getLoginObservable(String phone, String passWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", passWord);
        KLog.a(map.toString());
        return mNewsService.getLogin(map);
    }

    public Observable<BaseRspObj> getUpPassObservable(String phone, String passWord,String oldPassWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", passWord);
        map.put("oldPassWord",oldPassWord);
        KLog.a(map.toString());
        return mNewsService.getUpPass(map);
    }

    public Observable<BaseRspObj<ProjectObjectBean>> getProjectListObservable(String pageNum,String userType, String status,String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("userType", userType);
        map.put("status", status);
        map.put("userId", userId);
        KLog.a(map.toString());
        return mNewsService.getProjectList(map);
    }

    public Observable<BaseRspObj<LinkObjectBean>> getProjectLinkObservable(String pageNum, String projectId) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("projectId", projectId);
        KLog.a(map.toString());
        return mNewsService.getProjectLinkList(map);
    }

    public Observable<BaseRspObj<NodeObjectBean>> getProjectNodeObservable(String pageNum, String projectId,String taskCode) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("projectId", projectId);
        map.put("taskCode",taskCode);
        KLog.a(map.toString());
        return mNewsService.getProjectNodeList(map);
    }
}
