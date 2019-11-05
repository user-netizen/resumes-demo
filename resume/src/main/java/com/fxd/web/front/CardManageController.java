package com.fxd.web.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxd.dto.CardExecution;
import com.fxd.entity.PersonInfo;
import com.fxd.entity.ResumesCard;
import com.fxd.entity.ResumesInfo;
import com.fxd.entity.ResumesLabel;
import com.fxd.enums.StateEnum;
import com.fxd.service.ResumesCardService;
import com.fxd.service.ResumesLabelService;
import com.fxd.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("front")
public class CardManageController {
    private ResumesCard resumesCard;
    private List<ResumesLabel> resumesLabelList;
    private ResumesLabel resumesLabel;
    private ResumesInfo resumesInfo;
    private PersonInfo personInfo;

    @Autowired
    private ResumesCardService resumesCardService;

    @Autowired
    private ResumesLabelService resumesLabelService;

    /**
     * 查询单个数据 条件用户的ID值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getCardByUserId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getCardByUserId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        Map<String, Object> modelMapAll = new HashMap<String, Object>();
        //测试情况，从前端传的userId
        Long id = HttpServletRequestUtil.getLong(request, "userId");
        //正式情况，从session获取登录用户的
        if (id > -1) {
            try {
                //根据用户ID查询库，返回数据
                resumesCard = resumesCardService.getResumesCardByUserId(id);
                if (resumesCard != null) {
                    modelMap.put("success", true);
                    modelMap.put("resumesCard", resumesCard);
                } else {
                    modelMap.put("errMsg", StateEnum.NULL_INFO.getState());
                }
                return modelMap;
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("error", "error");
        }
        return modelMap;
    }

    /**
     * 查询所有并分页数据 条件：随机,rowIndex,pageSize
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getAllCard", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getAllCard(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //查询条件
        String name = HttpServletRequestUtil.getString(request, "name");
        //查询固定条件
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        try {
            resumesInfo = new ResumesInfo();
            resumesInfo.setResumesInfoName(name);

            resumesCard = new ResumesCard();
            resumesCard.setResumesInfo(resumesInfo);

            CardExecution ce = resumesCardService.getResumesCardList(resumesCard, pageIndex, pageSize);
//            int s = ce.getResumesCardList().size();
//
//            List<ResumesLabel> resumesLabelList =new ArrayList<ResumesLabel>();
//            for (int i = 0; i < s; i++) {
//                System.out.println(ce.getResumesCardList().get(i).getResumesCardId());
//                resumesLabelList.addAll(resumesLabelService.getLabelByCardIdList(ce.getResumesCardList().get(i).getResumesCardId()));
//            }
            modelMap.put("success", true);
            modelMap.put("total", ce.getCount());
            modelMap.put("resumesCardList", ce.getResumesCardList());
//            modelMap.put("resumesLabelList",resumesLabelList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 插入单条数据 条件：用户ID和infoId
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addCard", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addCard(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        String cardStr = HttpServletRequestUtil.getString(request, "cardStr");
        System.out.println(cardStr);

        ObjectMapper mapper = new ObjectMapper();
        //读取
        try {
            resumesCard = mapper.readValue(cardStr, ResumesCard.class);
        } catch (Exception e) {
            modelMap.put("failed", true);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //添加
        if (resumesCard != null) {
            CardExecution ce;
            try {
                ce = resumesCardService.addResumesCard(resumesCard);
                if (ce.getState() == StateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("failed", true);
                    modelMap.put("errMsg", ce.getStateInfo());
                }
            } catch (Exception e){
                modelMap.put("failed", true);
                modelMap.put("errMsg", e.getMessage());
            }

        } else {
            modelMap.put("failed", true);
            modelMap.put("errMsg", "请输入信息");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 更新单条数据 条件当前用户ID
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyCard", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyCard(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        String cardStr = HttpServletRequestUtil.getString(request, "cardStr");
        System.out.println(cardStr);

        ObjectMapper mapper = new ObjectMapper();
        //读取
        try {
            resumesCard = mapper.readValue(cardStr, ResumesCard.class);
        } catch (Exception e) {
            modelMap.put("failed", true);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //添加
        if (resumesCard != null) {
            CardExecution ce;
            try {
                ce = resumesCardService.updateResumesCard(resumesCard);
                if (ce.getState() == StateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("failed", true);
                    modelMap.put("errMsg", ce.getStateInfo());
                }
            } catch (Exception e){
                modelMap.put("failed", true);
                modelMap.put("errMsg", e.getMessage());
            }

        } else {
            modelMap.put("failed", true);
            modelMap.put("errMsg", "请输入信息");
            return modelMap;
        }
        return modelMap;
    }
}
