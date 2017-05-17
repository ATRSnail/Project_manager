package com.project.project_manager.repository;

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

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.project.project_manager.common.ApiConstants.ADD_TASK_DIC_URL;
import static com.project.project_manager.common.ApiConstants.AUDIT_STATUS_URL;
import static com.project.project_manager.common.ApiConstants.DEL_IMG_URL;
import static com.project.project_manager.common.ApiConstants.INDEX_DIC_URL;
import static com.project.project_manager.common.ApiConstants.LOGIN_URL;
import static com.project.project_manager.common.ApiConstants.NEWS_URL;
import static com.project.project_manager.common.ApiConstants.PROJECT_LINKS_URL;
import static com.project.project_manager.common.ApiConstants.PROJECT_LIST_URL;
import static com.project.project_manager.common.ApiConstants.PROJECT_NODES_URL;
import static com.project.project_manager.common.ApiConstants.PROJECT_TASKS_URL;
import static com.project.project_manager.common.ApiConstants.TASK_DETAIL_URL;
import static com.project.project_manager.common.ApiConstants.TASK_LINK_DETAILS_URL;
import static com.project.project_manager.common.ApiConstants.TASK_UN_DONE_URL;
import static com.project.project_manager.common.ApiConstants.UPDATE_PASS_WORD;
import static com.project.project_manager.common.ApiConstants.UPLOAD_FILE_URL;
import static com.project.project_manager.common.ApiConstants.WAIT_DO_DETAIL_URL;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsService {

    @FormUrlEncoded
    @POST(NEWS_URL)
    Observable<RspNewsBean> getNewsList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(LOGIN_URL)
    Observable<RspLoginBean> getLogin(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(UPDATE_PASS_WORD)
    Observable<BaseRspObj> getUpPass(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(PROJECT_LIST_URL)
    Observable<BaseRspObj<ProjectObjectBean>> getProjectList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(PROJECT_LINKS_URL)
    Observable<BaseRspObj<LinkObjectBean>> getProjectLinkList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(PROJECT_NODES_URL)
    Observable<BaseRspObj<NodeObjectBean>> getProjectNodeList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(PROJECT_TASKS_URL)
    Observable<BaseRspObj<TaskObjectBean>> getProjectTaskList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(TASK_DETAIL_URL)
    Observable<BaseRspObj<PvoObjectBean>> getProjectPvoList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(WAIT_DO_DETAIL_URL)
    Observable<BaseRspObj<TaskDictionaryObjectBean>> getTaskDicList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(INDEX_DIC_URL)
    Observable<BaseRspObj<DictObjBean>> getDicDicList(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(ADD_TASK_DIC_URL)
    Observable<BaseRspObj> addTaskDis(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(TASK_LINK_DETAILS_URL)
    Observable<BaseRspObj<TaskDetailsObjBean>> getTaskDetails(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(TASK_UN_DONE_URL)
    Observable<BaseRspObj<UndoneTaskObjBean>> getUnDoTaskDetails(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(AUDIT_STATUS_URL)
    Observable<BaseRspObj> getAuditStatus(@FieldMap Map<String, String> map);

    @POST(UPLOAD_FILE_URL)
    Observable<BaseRspObj> uploadImage(@Body MultipartBody body);

    @FormUrlEncoded
    @POST(DEL_IMG_URL)
    Observable<BaseRspObj> delImage(@FieldMap Map<String, String> map);
}
