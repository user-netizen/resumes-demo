package com.fxd.web.front;

import com.fxd.dto.LocalAuthExecution;
import com.fxd.dto.PersonExecution;
import com.fxd.entity.LocalAuth;
import com.fxd.entity.PersonInfo;
import com.fxd.entity.User;
import com.fxd.enums.StateEnum;
import com.fxd.service.LocalAuthService;
import com.fxd.service.PersonInfoService;
import com.fxd.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "front")
public class LoginServlet {

    @Autowired
    private LocalAuthService localAuthService;
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "sign", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> sign(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Object currentUserObj = request.getSession().getAttribute("currentUser");
        if (currentUserObj != null) {
            modelMap.put("success", true);
            modelMap.put("data", currentUserObj);
        } else {
            modelMap.put("errMsg", "请重新登录");
        }
        return modelMap;
    }


    /**
     * 思路
     * 前端传来邮箱检测tb_person_info中是否有数据
     * 登录
     * 邮箱检测数据库有，再对比密码是正确 设置session 成功返回
     * 邮箱检测数据库没有，邮箱和密码提交成功 设置session 成功返回
     */

    @RequestMapping(value = "loginin", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> loginin(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String name = HttpServletRequestUtil.getString(request, "name");
        String email = HttpServletRequestUtil.getString(request, "email");
        String pwd = HttpServletRequestUtil.getString(request, "pwd");
        String gender = HttpServletRequestUtil.getString(request, "gender");

        if (request.getSession().getAttribute("currentUser") != null) {
            request.getSession().invalidate();
        }

        // 对比数据库tb_person_info中email
        try {
            PersonInfo personInfo = personInfoService.getPersonInfo(email);
            if (personInfo != null) {
                try {
                    //有
                    Long userId = personInfo.getUserId();
                    //输入pwd
                    LocalAuth localAuth = localAuthService.getLocalAuth(userId);
                    //对比
                    if (pwd.equals(localAuth.getPassword())) {
                        // 密码正确
                        // 设置session
                        User user = new User();
                        user.setId(localAuth.getPersonInfo().getUserId());
                        user.setName(localAuth.getPersonInfo().getName());
                        user.setEmail(localAuth.getPersonInfo().getEmail());
                        request.getSession().setAttribute("currentUser", user);

                        //封装对象 用户ID 邮箱 用户名 设置session
                        User currentUser = (User) request.getSession().getAttribute("currentUser");
                        modelMap.put("success", true);
                        modelMap.put("user", currentUser);
                    } else {
                        //密码不正确
                        //提示用户
                        modelMap.put("errMsg", "密码错误");
                    }
                } catch (Exception e) {
                    modelMap.put("errMsg", "错误" + e.getMessage());
                }
            } else {
                try {
                    //没有
                    PersonInfo personInfo1 = new PersonInfo();
                    personInfo1.setName(name);
                    //personInfo1.setProfileImg();
                    personInfo1.setEmail(email);
                    personInfo1.setGender(gender);
                    personInfo1.setEnableStatus(0);
                    personInfo1.setUserType(1);
                    personInfo1.setCreatTime(new Date());
                    personInfo1.setLastEditTime(new Date());
                    PersonExecution pe = personInfoService.addPersonInfo(personInfo1);
                    if (pe.getState() == StateEnum.SUCCESS.getState()) {
                        //输入pwd
                        //再次查询邮箱 返回userId
                        PersonInfo personInfo2 = personInfoService.getPersonInfo(email);
                        personInfo2.getUserId();

                        LocalAuth localAuth = new LocalAuth();
                        localAuth.setPersonInfo(personInfo2);
                        localAuth.setUsername(name);
                        localAuth.setPassword(pwd);
                        localAuth.setCreateTime(new Date());
                        localAuth.setLastEditTime(new Date());
                        LocalAuthExecution le = localAuthService.addLocalAuth(localAuth);
                        try {
                            if (le.getState() == StateEnum.SUCCESS.getState()) {
                                try {
                                    //有
                                    Long userId = personInfo2.getUserId();
                                    //输入pwd
                                    LocalAuth localAuth1 = localAuthService.getLocalAuth(userId);
                                    //对比
                                    if (pwd.equals(localAuth1.getPassword())) {
                                        // 密码正确
                                        // 设置session
                                        User user = new User();
                                        user.setId(localAuth.getPersonInfo().getUserId());
                                        user.setName(localAuth.getPersonInfo().getName());
                                        user.setEmail(localAuth.getPersonInfo().getEmail());
                                        request.getSession().setAttribute("currentUser", user);

                                        //封装对象 用户ID 邮箱 用户名 设置session
                                        User currentUser = (User) request.getSession().getAttribute("currentUser");
                                        modelMap.put("success", true);
                                        modelMap.put("user", currentUser);
                                    } else {
                                        modelMap.put("errMsg", "LocalAuth提交错误，请重试");
                                    }
                                } catch (Exception e) {
                                    modelMap.put("errMsg", e.getMessage());
                                }

                            } else {
                                modelMap.put("errMsg", "PersonInfo提交错误，请重试");
                            }
                        } catch (Exception e) {
                            modelMap.put("errMsg", e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    modelMap.put("errMsg", e.getMessage());
                }
            }

        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String email = HttpServletRequestUtil.getString(request, "email");
        String pwd = HttpServletRequestUtil.getString(request, "pwd");
        try {
            PersonInfo personInfo = personInfoService.getPersonInfo(email);
            if (personInfo != null) {
                try {
                    //有
                    Long userId = personInfo.getUserId();
                    //输入pwd
                    LocalAuth localAuth = localAuthService.getLocalAuth(userId);
                    //对比
                    if (pwd.equals(localAuth.getPassword())) {
                        // 密码正确
                        // 设置session
                        User user = new User();
                        user.setId(localAuth.getPersonInfo().getUserId());
                        user.setName(localAuth.getPersonInfo().getName());
                        user.setEmail(localAuth.getPersonInfo().getEmail());
                        request.getSession().setAttribute("currentUser", user);

                        //封装对象 用户ID 邮箱 用户名 设置session
                        User currentUser = (User) request.getSession().getAttribute("currentUser");
                        modelMap.put("success", true);
                        modelMap.put("user", currentUser);
                    } else {
                        //密码不正确
                        //提示用户
                        modelMap.put("errMsg", "密码错误");
                    }
                } catch (Exception e) {
                    modelMap.put("errMsg", "错误" + e.getMessage());
                }
            } else {
                modelMap.put("errMsg", "邮箱不存在");
            }

        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> register(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        String name = HttpServletRequestUtil.getString(request, "name");
        String email = HttpServletRequestUtil.getString(request, "email");
        String pwd = HttpServletRequestUtil.getString(request, "pwd");
        String gender = HttpServletRequestUtil.getString(request, "gender");

        try {
            PersonInfo personInfo = personInfoService.getPersonInfo(email);
            System.out.println(personInfo);

            if (personInfo == null) {
                try {
                    //没有
                    PersonInfo personInfo1 = new PersonInfo();
                    personInfo1.setName(name);
                    personInfo1.setProfileImg("");
                    personInfo1.setEmail(email);
                    personInfo1.setGender(gender);
                    personInfo1.setEnableStatus(0);
                    personInfo1.setUserType(1);
                    personInfo1.setCreatTime(new Date());
                    personInfo1.setLastEditTime(new Date());
                    PersonExecution pe = personInfoService.addPersonInfo(personInfo1);
                    if (pe.getState() == StateEnum.SUCCESS.getState()) {
                        //输入pwd
                        //再次查询邮箱 返回userId
                        PersonInfo personInfo2 = personInfoService.getPersonInfo(email);
                        personInfo2.getUserId();

                        LocalAuth localAuth = new LocalAuth();
                        localAuth.setUsername(name);
                        localAuth.setPassword(pwd);
                        localAuth.setCreateTime(new Date());
                        localAuth.setLastEditTime(new Date());

                        localAuth.setPersonInfo(personInfo2);

                        LocalAuthExecution le = localAuthService.addLocalAuth(localAuth);
                        try {
                            if (le.getState() == StateEnum.SUCCESS.getState()) {
                                try {
                                    //有
                                    Long userId = personInfo2.getUserId();
                                    //输入pwd
                                    LocalAuth localAuth1 = localAuthService.getLocalAuth(userId);
                                    //对比
                                    if (pwd.equals(localAuth1.getPassword())) {
                                        // 密码正确
                                        // 设置session
                                        User user = new User();
                                        user.setId(localAuth.getPersonInfo().getUserId());
                                        user.setName(localAuth.getPersonInfo().getName());
                                        user.setEmail(localAuth.getPersonInfo().getEmail());
                                        request.getSession().setAttribute("currentUser", user);

                                        //封装对象 用户ID 邮箱 用户名 设置session
                                        User currentUser = (User) request.getSession().getAttribute("currentUser");
                                        modelMap.put("success", true);
                                        modelMap.put("user", currentUser);
                                    } else {
                                        modelMap.put("errMsg", "LocalAuth提交错误，请重试");
                                    }
                                } catch (Exception e) {
                                    modelMap.put("errMsg", e.getMessage());
                                }

                            } else {
                                modelMap.put("errMsg", "PersonInfo提交错误，请重试");
                            }
                        } catch (Exception e) {
                            modelMap.put("errMsg", e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    modelMap.put("errMsg", e.getMessage());
                }
            }else {
                modelMap.put("errMsg", "邮箱已存在，请重试");
            }
        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
            System.out.println(e.getMessage());
        }

        return modelMap;
    }
}
