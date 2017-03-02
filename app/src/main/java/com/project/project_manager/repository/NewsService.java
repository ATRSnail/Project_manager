package com.project.project_manager.repository;

import com.project.project_manager.common.ApiConstants;
import com.project.project_manager.mvp.entity.LinkObjectBean;
import com.project.project_manager.mvp.entity.NodeObjectBean;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.response.RspLoginBean;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsService {

    @FormUrlEncoded
    @POST(ApiConstants.NEWS_URL)
    Observable<RspNewsBean> getNewsList(@FieldMap Map<String, String> map);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Url String photoPath);


    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_URL)
    Observable<RspLoginBean> getLogin(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_PASS_WORD)
    Observable<BaseRspObj> getUpPass(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.PROJECT_LIST_URL)
    Observable<BaseRspObj<ProjectObjectBean>> getProjectList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.PROJECT_LINKS_URL)
    Observable<BaseRspObj<LinkObjectBean>> getProjectLinkList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.PROJECT_NODES_URL)
    Observable<BaseRspObj<NodeObjectBean>> getProjectNodeList(@FieldMap Map<String, String> map);

}
