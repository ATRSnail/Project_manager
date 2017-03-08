package com.project.project_manager.repository;

import com.project.project_manager.common.ApiConstants;
import com.project.project_manager.mvp.entity.DictObjBean;
import com.project.project_manager.mvp.entity.LinkObjectBean;
import com.project.project_manager.mvp.entity.NodeObjectBean;
import com.project.project_manager.mvp.entity.ProjectObjectBean;
import com.project.project_manager.mvp.entity.PvoObjectBean;
import com.project.project_manager.mvp.entity.TaskDetailsObjBean;
import com.project.project_manager.mvp.entity.TaskDictionaryObjectBean;
import com.project.project_manager.mvp.entity.TaskObjectBean;
import com.project.project_manager.mvp.entity.response.RspLoginBean;
import com.project.project_manager.mvp.entity.response.RspNewsBean;
import com.project.project_manager.mvp.entity.response.base.BaseRspObj;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST(ApiConstants.PROJECT_TASKS_URL)
    Observable<BaseRspObj<TaskObjectBean>> getProjectTaskList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.TASK_DETAIL_URL)
    Observable<BaseRspObj<PvoObjectBean>> getProjectPvoList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(ApiConstants.WAIT_DO_DETAIL_URL)
    Observable<BaseRspObj<TaskDictionaryObjectBean>> getTaskDicList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(ApiConstants.INDEX_DIC_URL)
    Observable<BaseRspObj<DictObjBean>> getDicDicList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(ApiConstants.ADD_TASK_DIC_URL)
    Observable<BaseRspObj> addTaskDis(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(ApiConstants.TASK_LINK_DETAILS_URL)
    Observable<BaseRspObj<TaskDetailsObjBean>> getTaskDetails(@FieldMap Map<String, String> map);
}
