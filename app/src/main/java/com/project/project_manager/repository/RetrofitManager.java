package com.project.project_manager.repository;

import android.text.TextUtils;
import android.util.SparseArray;

import com.project.project_manager.App;
import com.project.project_manager.common.ApiConstants;
import com.project.project_manager.common.Constants;
import com.project.project_manager.common.UsrMgr;
import com.project.project_manager.mvp.entity.DictObjBean;
import com.project.project_manager.mvp.entity.LinkObjectBean;
import com.project.project_manager.mvp.entity.NodeObjectBean;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.PvoObjectBean;
import com.project.project_manager.mvp.entity.TaskDetailsObjBean;
import com.project.project_manager.mvp.entity.TaskDictionaryObjectBean;
import com.project.project_manager.mvp.entity.TaskObjectBean;
import com.project.project_manager.mvp.entity.UndoneTaskObjBean;
import com.project.project_manager.mvp.entity.response.RspLoginBean;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;
import com.project.project_manager.utils.NetUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    public Observable<BaseRspObj> getUpPassObservable(String phone, String passWord, String oldPassWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", passWord);
        map.put("oldPassWord", oldPassWord);
        KLog.a(map.toString());
        return mNewsService.getUpPass(map);
    }

    public Observable<BaseRspObj<ProjectObjectBean>> getProjectListObservable(String pageNum, String userType, String status, String userId) {
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

    public Observable<BaseRspObj<NodeObjectBean>> getProjectNodeObservable(String pageNum, String projectId, String taskCode) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("projectId", projectId);
        map.put("taskCode", taskCode);
        KLog.a(map.toString());
        return mNewsService.getProjectNodeList(map);
    }

    public Observable<BaseRspObj<TaskObjectBean>> getProjectTaskObservable(String pageNum, String projectId, String userId, String userType) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("projectId", projectId);
        map.put("userId", userId);
        map.put("userType", userType);
        KLog.a(map.toString());
        return mNewsService.getProjectTaskList(map);
    }


    public Observable<BaseRspObj<PvoObjectBean>> getProjectPvoObservable(String projectTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("projectTaskId", projectTaskId);
        KLog.a(map.toString());
        return mNewsService.getProjectPvoList(map);
    }

    public Observable<BaseRspObj<TaskDictionaryObjectBean>> getTaskDicList(String userId, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        if (!TextUtils.isEmpty(id))
            map.put("id", id);
        KLog.a(map.toString());
        return mNewsService.getTaskDicList(map);
    }

    public Observable<BaseRspObj<DictObjBean>> getDicDicList(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        KLog.a(map.toString());
        return mNewsService.getDicDicList(map);
    }

    public Observable<BaseRspObj> addTaskDis(String codes, String names, String type, String projectId, String companyType,String positions) {
        Map<String, String> map = new HashMap<>();
        map.put("codes", codes);
        map.put("names", names);
        map.put("type", type);
        map.put("projectId", projectId);
        map.put("companyType", companyType);
        map.put("positions",positions);
        KLog.a(map.toString());
        return mNewsService.addTaskDis(map);
    }

    public Observable<BaseRspObj<TaskDetailsObjBean>> getTaskDetails(String projectTaskId, String userId, String status, String userType) {
        Map<String, String> map = new HashMap<>();
        map.put("projectTaskId", projectTaskId);
        map.put("userId", userId);
        map.put("status", status);
        map.put("userType", userType);
        KLog.a(map.toString());
        return mNewsService.getTaskDetails(map);
    }

    public Observable<BaseRspObj<UndoneTaskObjBean>> getUnDoTaskDetails(String pageNum, String userId, String userType) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("numPerPage", Constants.numPerPage + "");
        map.put("userId", userId);
        map.put("userType", userType);
        KLog.a(map.toString());
        return mNewsService.getUnDoTaskDetails(map);
    }

    public Observable<BaseRspObj> getAuditStatus(String taskUserId, String status, String opinion, String projectTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("taskUserId", taskUserId);
        map.put("status", status);
        map.put("opinion", opinion);
        map.put("userId", UsrMgr.getUseId());
        map.put("projectTaskId", projectTaskId);
        map.put("userType", UsrMgr.getUseType());
        KLog.a(map.toString());
        return mNewsService.getAuditStatus(map);
    }


    public Observable<BaseRspObj> delImage(String resourceUrl) {
        Map<String, String> map = new HashMap<>();
        map.put("resourceUrl", resourceUrl);
        KLog.a(map.toString());
        return mNewsService.delImage(map);
    }


    public Observable<BaseRspObj> uploadImage(String uid, List<String> pictures, String video, String projectTaskId,String reallyPosition) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i=0;i<pictures.size();i++){
            File file = new File(pictures.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("applicaiton/otcet-stream"), file);
            builder.addFormDataPart("picture"+i, file.getName(), requestBody);
        }
        if (!TextUtils.isEmpty(video)) {
            File videoFile = new File(video);
            KLog.a("video--->" + video);
            RequestBody requestVideoBody = RequestBody.create(MediaType.parse("applicaiton/otcet-stream"), videoFile);
            builder.addFormDataPart("video", videoFile.getName(), requestVideoBody);
        }
        builder.addFormDataPart("projectTaskId", projectTaskId);
        builder.addFormDataPart("uid", uid);
        builder.setType(MultipartBody.FORM);

        Map<String, String> map = new HashMap<>();
        map.put("projectTaskId", projectTaskId);
        map.put("uid", uid);
        map.put("picture", pictures.toString());
        map.put("video", video);
        map.put("reallyPosition",reallyPosition);
        KLog.a(map.toString());
        return mNewsService.uploadImage(builder.build());
    }

    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key,
                                                       String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     *
     * @param key       同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     *
     * @param parts    Part集合
     * @param key      参数名（name属性）
     * @param value    文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     *
     * @param builder 用于构建MultipartBody的Builder
     * @param key     参数名（name属性）
     * @param value   文本内容
     */
    public static MultipartBody.Builder addTextPart(MultipartBody.Builder builder,
                                                    String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key, null, requestBody);
        return builder;
    }
}
