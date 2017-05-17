package com.project.project_manager.common;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class ApiConstants {

    //线下
    public static final String NETEAST_HOST = "http://172.16.10.15:9400/";
    public static final String NETEAST_IMG_HOST = "http://files.heweather.com/cond_icon/%s.png";

    //线上
//    public static final String NETEAST_HOST = "http://59.108.94.40:9200/";

    public static String IMG_HOST = NETEAST_HOST+"file/";
    public static String getHost() {
        return NETEAST_HOST;
    }

    public static final String NEWS_URL = "/gsl-api/gsl/news/newsList";

    //登录
    public static final String LOGIN_URL = "/gcjg-api/sysUser/login";

    //项目列表
    public static final String PROJECT_LIST_URL = "/gcjg-api/project/projectList";

    //修改密码
    public static final String UPDATE_PASS_WORD = "/gcjg-api/sysUser/upPass";

    //归档环节列表
    public static final String PROJECT_LINKS_URL = "/gcjg-api/project/linkList";

    //归档节点列表
    public static final String PROJECT_NODES_URL = "/gcjg-api/project/nodeList";

    //待办节点列表
    public static final String PROJECT_TASKS_URL = "/gcjg-api/project/getTaskList";

    //查询归档节点详情
    public static final String TASK_DETAIL_URL = "/gcjg-api/project/getTask";

    //获取环节接口
    public static final String WAIT_DO_DETAIL_URL = "/gcjg-api/waitDo/listLink";

    //获取字典
    public static final String INDEX_DIC_URL = "/gcjg-api/index/getDic";

    //添加待办接口
    public static final String ADD_TASK_DIC_URL = "/gcjg-api/project/task/addTaskDis";

    //查询待办节点详情
    public static final String TASK_LINK_DETAILS_URL = "/gcjg-api/project/getTaskDetails";

    //查询未完成任务接口
    public static final String TASK_UN_DONE_URL = "/gcjg-api/project/undoneTask";

    //审核任务接口
    public static final String AUDIT_STATUS_URL = "/gcjg-api/project/auditStatus";


    //审核任务接口
    public static final String DEL_IMG_URL = "/gcjg-api/project/delResource";

    //提交审核接口
    public static final String UPLOAD_FILE_URL = "/gcjg-api/upload/fileUpload";
}
