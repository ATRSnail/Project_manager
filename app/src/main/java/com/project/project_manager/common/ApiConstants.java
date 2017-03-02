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
//    public static final String NETEAST_HOST = "http://59.108.94.40:9100/";

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


}
