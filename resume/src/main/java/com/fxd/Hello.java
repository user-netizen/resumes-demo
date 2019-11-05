package com.fxd;

import com.fxd.entity.PersonInfo;
import com.fxd.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Hello {
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "name", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> name(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", "你好 Spring Boot!");
        return modelMap;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> index(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        PersonInfo personInfo = personInfoService.getPersonInfo("mige933@163.com");
        modelMap.put("success", personInfo);
        return modelMap;
    }
}
