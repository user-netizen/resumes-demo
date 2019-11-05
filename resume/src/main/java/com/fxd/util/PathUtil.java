package com.fxd.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {

    private static String separator = System.getProperty("file.separator");
    private static String winPath = "D:/";
    private static String linuxPath = "/home/work";
    private static String macPath = "/Users/zhangtao/";
    private static String resumesRelevantPath = "/upload/resume/";

    /**
     * 返回项目图片的根路径
     *
     * @return
     */
    public static String getImgBasePath() {
        //获取电脑系统
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            //basePath = "D:/";
            basePath = winPath;
        } else {
//            basePath = "/Users/zhangtao/";
//            basePath = macPath;
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    /**
     * 项目图片自定义创建子路径
     *
     * @param id
     * @return
     */
    public static String getCostumeImagePath(long id) {
//        String imagePath = "upload/resumes/" + id + "/";
        String imagePath = resumesRelevantPath + id + "/";
        return imagePath.replace("/", separator);
    }
}
