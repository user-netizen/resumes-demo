package com.fxd.web.front;

import com.fxd.dto.LabelExecution;
import com.fxd.entity.ResumesLabel;
import com.fxd.enums.StateEnum;
import com.fxd.service.ResumesLabelService;
import com.fxd.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("front")
public class LabelManageController {
    private ResumesLabel resumesLabel;

    @Autowired
    private ResumesLabelService resumesLabelService;

    @RequestMapping(value = "getLabelByCardId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getLabelByCardId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        long cardId = HttpServletRequestUtil.getLong(request, "id");
        if (cardId > -1) {
            try {
                LabelExecution le = resumesLabelService.getLabelByCardIdList(cardId);
                if (le.getResumesLabelList().size() > 0) {
                    modelMap.put("success", true);
                    modelMap.put("total", le.getCount());
                    modelMap.put("resumesLabelList", le.getResumesLabelList());
                } else {
                    modelMap.put("errMsg", "没有该信息");
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("error", "error");
            return modelMap;
        }

    }

    /**
     * 添加批量数据 添加批量list数据
     *
     * @param labelList
     * @param request
     * @return
     */
    @RequestMapping(value = "addBatchLabel", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addBatchLabel(@RequestBody List<ResumesLabel> labelList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        for (ResumesLabel pc : labelList) {
            pc.setCreateTime(new Date());
            pc.setLastEditTime(new Date());
        }

        if (labelList != null && labelList.size() > 0) {
            try {
                LabelExecution le = resumesLabelService.addBatchResumesLabel(labelList);
                if (le.getState() == StateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个Label");
        }
        return modelMap;
    }
}
