package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.User;
import com.example.willsea.exception.SubException;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yt on 2018/6/22.
 */
@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IBottleService bottleService;


    @GetMapping(value = "/back/user")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "limit", defaultValue = "8") int limit, Model model){
        List<User> users = userService.queryAll((page - 1) * limit,limit);
        Integer recordNum = userService.queryTotalNumber();
        model.addAttribute("users", users);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return "/back/user";
    }
    @PostMapping(value = "/user/detail/addToTypeList")
    @ResponseBody
    public RestResponse addToTypeList(@Param(value ="target")Integer target,@Param(value="type")String type,HttpServletRequest request)
    {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        if(cookieUser!=null)
        {
            if(target!=null)
            {
                if(type.equals("favorite"))
                    userService.favoriteUser(cookieUser.getUid(),target);
                else if(type.equals("black"))
                    userService.avoidUser(cookieUser.getUid(),target);
                return RestResponse.ok();
            }
        }
        return RestResponse.fail();
    }

    @PostMapping(value="/user/usercenter/freeFromTypeList")
    @ResponseBody
    public RestResponse freeFromTypeList(@Param(value = "source")Integer source, @Param(value = "target")Integer target, @Param(value="type") String type) {
        if (source != null && target != null) {
            if (type.equals("bottle")) {
                Integer bid = target;
                bottleService.deleteBottle(target);

            } else {
                User sourceUser = userService.queryById(source);
                User targetUser = userService.queryById(target);
                if (type.equals("black")) {
                    userService.freeFromBlackList(sourceUser, targetUser);
                } else if (type.equals("favorite")) {
                    userService.freeFromFavoriteList(sourceUser, targetUser);
                }
            }
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @GetMapping(value = "/user/sign")
    public  String showSignIn(HttpServletRequest request)
    {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        if(cookieUser!=null)
        {
            return "redirect:/user/usercenter/"+cookieUser.getUid();
        }
        return "user/sign";
    }

    @PostMapping(value = "/user/sign/submit")
    public String signInPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=userService.queryToVerify(username,password);
        if(user!=null)
        {
            request.getSession().setAttribute("cookieUser",user);
            return "redirect:/user/usercenter/"+user.getUid();
        }
        return "error/404";
    }
    @RequestMapping(value="/user/usercenter/{uid}")    //用户中心控制器
    public String showUserCenter(@PathVariable Integer uid, Model model,HttpServletRequest request) {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        User user=cookieUser;

        if(user!=null)
        {
            request.getSession().setAttribute("cookieUser",user);
            Integer pageLimit=9;
            Integer offset=(1-1)*pageLimit;
            List<Bottle>  bottles=bottleService.queryByAuthor(user.getUid(),offset,pageLimit);
            List<Integer> bids=new ArrayList<>();
            for (int i = 0; i < bottles.size(); ++i)
            {
                Bottle curBottle=bottles.get(i);
                bids.add(curBottle.getBid());
            }
            List<User>  favoriteList=userService.queryFavoriteList(user);
            List<User>  blackList=userService.queryBlackList(user);
            model.addAttribute("user",user);
            model.addAttribute("bottles",bottles);
            model.addAttribute("bids",bids);
            model.addAttribute("favoriteList",favoriteList);
            model.addAttribute("blackList",blackList);
            return "user/usercenter";
        }
        return "error/404";
    }
    @PostMapping(value="/user/index/logout")
    public String logoutPost(@Param(value = "uid")Integer uid,HttpServletRequest request)
    {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        if(cookieUser!=null)
            request.getSession().removeAttribute("cookieUser");
        return "redirect:user/index";
    }


    /**
     * 添加用户
     */
    @PostMapping(value = "/back/save")
    @ResponseBody
    public String saveUser(@RequestParam String type, @RequestParam String username,
                           @RequestParam String password, @RequestParam String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(type);
        user.setEmail(email);
        userService.insertUser(user);

        return "insert success";

    }
    @PostMapping(value = "/back/user/save")
    @ResponseBody
    public  RestResponse save(@RequestParam(value = "uid")Integer uid,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "forbidden")String forbidden) {
        try {
            User user=userService.queryById(uid);
            if(user==null)
            {
                return RestResponse.fail("data not exists");
            }
            user.setEmail(email);
            user.setForbidden(forbidden);
            userService.updateUser(user);
        }catch (Exception e){
            String msg = "deleting comment failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @PostMapping(value = "/back/user/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "uid")Integer uid) {
        try {
            User user=userService.queryById(uid);
            if(user==null)
            {
                return RestResponse.fail("data not exists.");
            }
            userService.deleteUser(uid);
        }catch (Exception e){
            String msg = "deleting comment failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @PostMapping(value = "/back/user/register")
    @ResponseBody
    public RestResponse register(@RequestParam(value = "username")String username,
                                 @RequestParam(value = "phone")String phone,
                                 @RequestParam(value = "email")String email,
                                 @RequestParam(value = "password")String password,
                                 @RequestParam(value = "sex")String sex,
                                 @RequestParam(value = "location")String location,
                                 @RequestParam(value = "birthday")String birthday) {
        birthday = birthday.replace("-", "");
        User user = userService.queryByUsername(username);
        if(user == null) {
            user = new User();
            user.setUsername(username);
            user.setTelephone(phone);
            user.setCreateTime("" + new Date().getTime());
            user.setBirthday(birthday);
            if(sex.equals("male")){
                user.setSex(1);
            }else if(sex.equals("female")){
                user.setSex(0);
            }
            user.setEmail(email);
            user.setPassword(password);
            user.setLocation(location);

            userService.insertUser(user);

        } else{
            return RestResponse.fail("用户名已经存在");
        }
        return RestResponse.ok();
    }


    private  void clearPrivateMessage(User user) {
        user.setPassword("");
        user.setEmail("");
        user.setTelephone("1");
        user.setLocation("");
    }
    @PostMapping(value = "/user/usercenter/modifyInfo")
    public RestResponse modifyUserInfo(@RequestParam(value = "uid")Integer uid,
                                       @RequestParam(value = "username")String username,
                                       @RequestParam(value = "email")String email){
        User user = userService.queryById(uid);
        if(user == null){
            return RestResponse.fail("user not exist");
        }
        if(userService.queryByUsername(username) != null){
            return RestResponse.ok("username exists!");
        }
        user.setUsername(username);
        user.setEmail(email);
        userService.updateUser(user);
        return RestResponse.ok("modify successfully.");
    }
    @PostMapping(value = "/user/usercenter/modifyPassword")
    @ResponseBody
    public RestResponse modifyUserPassword(@RequestParam(value = "uid")Integer uid,
                                           @RequestParam(value = "oldPassword")String oldPassword,
                                           @RequestParam(value = "newPassword")String newPassword){
        User user = userService.queryById(uid);
        if(user == null){
            throw new SubException("User does not exists!");
        }
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userService.updateUser(user);
        } else {
            return RestResponse.fail();
        }

        return RestResponse.ok();
    }
    @GetMapping(value = "/user/personalOcean/{uid}")
    public String showPersonalOcean(@PathVariable Integer uid,Model model,HttpServletRequest request)
    {
        System.out.println(uid);
        User cookieUser = (User) request.getSession().getAttribute("cookieUser");  //当前登录的用户
        User curUser=userService.queryById(uid);  //要访问的用户
        Integer offset=0;
        Integer limit=65535;
        List<Bottle> bottles=bottleService.queryByAuthor(uid,offset,limit);

        List<Integer> bids=new ArrayList<>();
        for(Integer i=0;i<bottles.size();++i)
        {
            if(bottles.get(i).getIsPrivate().equals("false"))
                bids.add(bottles.get(i).getBid());
        }
        System.out.println("public bottle size"+bids.size());
        model.addAttribute("cookieUser", cookieUser);
        model.addAttribute("bids", bids);
        model.addAttribute("curUser",curUser);
        return "user/personalOcean";
    }
}

// 1. 保持登陆状态，可以注销；
// 2. 漂流瓶的创建功能；（文字的存储，媒体文件的存储）
// 3. 评论的创建功能；
