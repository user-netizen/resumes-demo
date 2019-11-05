package com.fxd.web.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxd.dto.InfoExecution;
import com.fxd.entity.PersonInfo;
import com.fxd.entity.ResumesInfo;
import com.fxd.enums.StateEnum;
import com.fxd.exception.InfoException;
import com.fxd.service.ResumesInfoService;
import com.fxd.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/front")
public class ResumesManageController {
    private ResumesInfo resumesInfo;

    @Autowired
    private ResumesInfoService resumesInfoService;

    /**
     * 注意点 上传文件如出现后端无法获取值，可能在配置文件问题
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addInfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addInfo(@RequestParam("infoImg") MultipartFile infoImg,HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        /**
         * 分别获取前端form提交的infoStr和infoImg
         * 获取登录好的用户session值
         * 添加数据
         */
        String infoStr = HttpServletRequestUtil.getString(request, "infoStr");
        //System.out.println(infoStr);

        ObjectMapper mapper = new ObjectMapper();
        try {
            resumesInfo = mapper.readValue(infoStr, ResumesInfo.class);
        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

//        CommonsMultipartFile infoImg;
//        CommonsMultipartResolver cmr = new CommonsMultipartResolver(
//                request.getSession().getServletContext()
//        );
//        if (cmr.isMultipart(request)) {
//            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
//            infoImg = (CommonsMultipartFile) mhsr.getFile("infoImg");
//        } else {
//            modelMap.put("errMsg", "上传图片不能为空");
//            return modelMap;
//        }

        if (resumesInfo != null && infoImg != null) {
            InfoExecution ie;
            try {
                ie = resumesInfoService.addResumesInfo(resumesInfo,
                        infoImg.getInputStream(),
                        infoImg.getOriginalFilename());
                if (ie.getState() == StateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                    //附加
                    List<ResumesInfo> resumesInfoList =
                            (List<ResumesInfo>) request.getSession().getAttribute("infoList");
                    //第一次注册
                    if (resumesInfoList == null || resumesInfoList.size() == 0) {
                        resumesInfoList = new ArrayList<ResumesInfo>();
                    }
                    resumesInfoList.add(ie.getResumesInfo());
                    request.getSession().setAttribute("infoList", resumesInfoList);
                } else {
                    modelMap.put("errMsg", ie.getStateInfo());
                }
            } catch (InfoException e) {
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("errMsg", "请输入信息");
            return modelMap;
        }
    }

    /**
     * 单个处理 info信息
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyInfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        String infoStr = HttpServletRequestUtil.getString(request, "infoStr");
        ObjectMapper mapper = new ObjectMapper();
        try {
            resumesInfo = mapper.readValue(infoStr, ResumesInfo.class);
        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
        }
        if (resumesInfo != null) {
            InfoExecution ie;
            try {
                ie = resumesInfoService.modifyResumesInfo(resumesInfo, null, null);
                if (ie.getState() == StateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("errMsg", ie.getStateInfo());
                }
            } catch (InfoException e) {
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("errMsg", "请输入");
            return modelMap;
        }
    }

    /**
     * 单个处理图片 或 多个处理图片和文字
     * @param infoImg
     * @param request
     * @return
     */
    //, produces = {MediaType.TEXT_PLAIN_VALUE}
    @RequestMapping(value = "modifyInfoImg", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyInfoImg(@RequestParam("infoImg") MultipartFile infoImg, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        String infoStr = HttpServletRequestUtil.getString(request, "infoStr");
        ObjectMapper mapper = new ObjectMapper();
        try {
            resumesInfo = mapper.readValue(infoStr, ResumesInfo.class);
        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
        }

        //spring-mvc 配置xml 处理方式
//        CommonsMultipartFile infoImg;
//        CommonsMultipartResolver cmr = new CommonsMultipartResolver(
//                request.getSession().getServletContext()
//        );
//        if (cmr.isMultipart(request)) {
//            MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
//            infoImg = (CommonsMultipartFile) mhr.getFile("infoImg");
//        } else {
//            modelMap.put("errMsg", "上传图片不能为空");
//            return modelMap;
//        }


        if (resumesInfo != null) {
            InfoExecution ie;
            try {
//                if (infoImg == null) {
//                    ie = resumesInfoService.modifyResumesInfo(resumesInfo, null, null);
//                } else {
                    //spring boot 图片处理
                    System.out.println(infoImg.getSize());
                    ie = resumesInfoService.modifyResumesInfo(resumesInfo, infoImg.getInputStream(), infoImg.getOriginalFilename());
//                }

                if (ie.getState() == StateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("errMsg", ie.getStateInfo());
                }
            } catch (InfoException e) {
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("errMsg", "请输入");
            return modelMap;
        }
    }

    /**
     * 获取列表数据 条件可自行判断其一或其二，单个或多个 暂时不用(参数3，4不可用)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getInfoListByCondition", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getInfoListByCondition(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long id = HttpServletRequestUtil.getLong(request, "id");
        String name = HttpServletRequestUtil.getString(request, "name");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        try {
            //以下条件可自行判断其一或其二
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(id);

            ResumesInfo condition = new ResumesInfo();
            condition.setResumesInfoName(name);
            condition.setPersonInfo(personInfo);

            InfoExecution ie = resumesInfoService.getResumesInfoList(condition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("total", ie.getCount());
            modelMap.put("infoList", ie.getResumesInfoList());
        } catch (Exception e) {
            modelMap.put("error", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 获取数据 条件前端传用户ID
     *
     * @param request 请求参数名称在request
     * @return
     */
    @RequestMapping(value = "getInfoByUserId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getInfoByUserId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long id = HttpServletRequestUtil.getLong(request, "id");
        if (id > -1) {
            try {
                ResumesInfo resumesInfo = resumesInfoService.getResumesInfoByUserId(id);
                if (resumesInfo == null) {
                    modelMap.put("errMsg", StateEnum.NULL_INFO.getState());
                } else {
                    modelMap.put("data", resumesInfo);
                    modelMap.put("success", true);
                }
            } catch (Exception e) {
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("errMsg", "error");
        }
        return modelMap;
    }

    /**
     * 获取单个数据 条件infoID
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getInfoByInfoId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getInfoByInfoId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        long id = HttpServletRequestUtil.getLong(request, "infoId");
        System.out.println(id);
        if (id > -1) {
            System.out.println(id);
            try {
                ResumesInfo resumesInfo = resumesInfoService.getResumesInfoByInfoId(id);
                if (resumesInfo == null) {
                    modelMap.put("errMsg", StateEnum.NULL_INFO.getState());
                } else {
                    modelMap.put("success", true);
                    modelMap.put("data", resumesInfo);
                }
            } catch (Exception e) {
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("errMsg", "error");
            return modelMap;
        }
    }
}
