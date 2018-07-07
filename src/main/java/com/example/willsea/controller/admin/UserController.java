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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * 后台管理用户界面
     * @param page
     * @param limit
     * @param model
     * @return
     */
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

    /**
     * 用户添加关注或者黑名单
     * @param target
     * @param type
     * @param request
     * @return
     */
    @PostMapping(value = "/user/detail/addToTypeList")
    @ResponseBody
    public RestResponse addToTypeList(@Param(value ="target")Integer target,@Param(value="type")String type,HttpServletRequest request)
    {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        //验证登录状态
        if(cookieUser!=null)
        {
            //验证target不为空
            if(target!=null)
            {
                //根据type判断操作，黑白名单
                if(type.equals("favorite")){
                    userService.favoriteUser(cookieUser.getUid(),target);
                }
                else if(type.equals("black")){
                    userService.avoidUser(cookieUser.getUid(),target);
                }
                return RestResponse.ok();
            }
        }
        return RestResponse.fail();
    }

    /**
     * 用户移除关注或者黑名单,心愿瓶
     * @param source
     * @param target
     * @param type
     * @return
     */
    @PostMapping(value="/user/usercenter/freeFromTypeList")
    @ResponseBody
    public RestResponse freeFromTypeList(@Param(value = "source")Integer source, @Param(value = "target")Integer target, @Param(value="type") String type) {
        //验证数据有效性
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

    /**
     * 用户登陆界面
     * @param request
     * @return
     */
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

    /**
     * 用户登陆处理
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping(value = "/user/sign/submit")
    public String signInPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=userService.queryToVerify(username,password);
        //验证登录不为null时
        if(user!=null)
        {
            //设置cache用户
            request.getSession().setAttribute("cookieUser",user);
            return "redirect:/user/usercenter/"+user.getUid();
        }
        return "error/404";
    }

    /**
     *  用户中心
     * @param uid
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/user/usercenter/{uid}")
    public String showUserCenter(@PathVariable Integer uid, Model model,HttpServletRequest request) {
        //登录状态
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        User user=cookieUser;
        //不为空时展示用户中心
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
            model.addAttribute("cookieUser",cookieUser);
            model.addAttribute("user",user);
            model.addAttribute("bottles",bottles);
            model.addAttribute("bids",bids);
            model.addAttribute("favoriteList",favoriteList);
            model.addAttribute("blackList",blackList);
            return "user/usercenter";
        }
        return "error/404";
    }

    /**
     * 用户登出功能
     * @param uid
     * @param request
     * @return
     */
    @PostMapping(value="/user/index/logout")
    public String logoutPost(@Param(value = "uid")Integer uid,HttpServletRequest request)
    {
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        if(cookieUser!=null){
            //退出登录就是删除cache
            request.getSession().removeAttribute("cookieUser");
        }
        return "redirect:user/index";
    }


    /**
     * 后台添加用户
     * @param type
     * @param username
     * @param password
     * @param email
     * @return
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

    /**
     * 后台存储用户修改的数据
     * @param uid
     * @param email
     * @param forbidden
     * @return
     */
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

    /**
     *  后台删除用户数据
     * @param uid
     * @return
     */
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

    /**
     * 用户注册功能
     * @param username
     * @param phone
     * @param email
     * @param password
     * @param sex
     * @param location
     * @param birthday
     * @return
     */
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
        //用户名唯一
        if(user == null) {
            user = new User();
            user.setUsername(username);
            user.setTelephone(phone);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = df.format(new Date());
            user.setCreateTime(str);
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

    /**
     * 修改用户信息功能
     * @param uid
     * @param username
     * @param email
     * @return
     */
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

    /**
     * 修改用户密码
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/user/usercenter/modifyPassword")
    @ResponseBody
    public RestResponse modifyUserPassword(@RequestParam(value = "uid")Integer uid,
                                           @RequestParam(value = "oldPassword")String oldPassword,
                                           @RequestParam(value = "newPassword")String newPassword){
        User user = userService.queryById(uid);
        //验证条用户有效
        if(user == null){
            throw new SubException("User does not exists!");
        }
        //验证旧密码正确
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userService.updateUser(user);
        } else {
            return RestResponse.fail();
        }

        return RestResponse.ok();
    }

    /**
     * 用户上传多媒体文件（Audio、Video）
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/user/upload")
    public String upload(HttpServletRequest request, HttpServletResponse response){
        //  MultipartHttpServletRequest 用于在Spring 框架中上传文件
        //  StandardMultipartHttpServletRequest 用于在Spring中 上传文件 以及 用户表单数据
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        // 用于临时存储媒体文件所要保存的路径
        HashMap<String, String> map = new HashMap<>();
        // 用于建立音频文件格式表
        List<String> audios = new ArrayList<String>();
        //  用于建立视频文件格式表
        List<String> videos = new ArrayList<>();
        audios.add("mp3");
        audios.add("wav");
        audios.add("midi");
        videos.add("mp4");
        videos.add("avi");
        videos.add("wma");
        videos.add("flash");
        try {
            // 遍历请求所携带的文件
            Iterator<String> iterator = req.getFileNames();
            // 获取请求所携带的表单提交的参数信息
            String uid = req.getParameter("uid");
            System.out.println(uid);
            while (iterator.hasNext()) {
                // 获取提交的文件
                MultipartFile file = req.getFile(iterator.next());
                String fileName = file.getOriginalFilename();
                if(!fileName.equals("")){
                    int split = fileName.lastIndexOf(".");
                    // 获取字节流文件
                    byte[] bytes = file.getBytes();
                    String root = System.getProperty("user.dir");
                    String resourcePath = root + "\\src\\main\\resources\\static\\user\\";
                    System.out.println("real root path: " + root);
                    //  文件存储目录路径
                    File direct = new File(resourcePath + uid);
                    if(!direct.exists()){
                        direct.mkdir();
                    }
                    // 文件存储路径
                    fileName = resourcePath + uid + "\\" + fileName;
                    System.out.println(fileName);
                    File temp = new File(fileName);

                    // 输出流写出文件
                    OutputStream out = new FileOutputStream(temp);
                    out.write(bytes, 0, bytes.length);
                    out.close();

                    // 提取数据库要存储的文件路径
                    int splitPath = fileName.lastIndexOf("user");
                    String savePath = fileName.substring(splitPath - 1,fileName.length());
                    String format=savePath.substring(savePath.lastIndexOf(".")+1);
                    if(audios.contains(format))
                    {
                        map.put("audio",savePath);
                    }
                    else if(videos.contains(format))
                    {
                        map.put("video", savePath);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("warning in access");
        }
        // 根据获取的信息创建新的心愿瓶
        Bottle bottle = new Bottle();
        bottle.setAid(Integer.valueOf(req.getParameter("uid")));
        bottle.setTitle(req.getParameter("myTitle"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        bottle.setTime(date);
        bottle.setBtext(req.getParameter("myText"));
        if(map.get("audio")!=null){
            bottle.setBaudio(map.get("audio"));
        }
        else{
            bottle.setBaudio("NULL");
        }
        if(map.get("video")!=null){
            bottle.setBvideo(map.get("video"));
        }
        else{
            bottle.setBvideo("NULL");
        }
        if(req.getParameter("myPrivate") == null){
            bottle.setIsPrivate("false");
        }else {
            bottle.setIsPrivate("true");
        }
        // 创建心愿瓶
        bottleService.createBottle(bottle);
        return "redirect:/user/usercenter/" + req.getParameter("uid");
    }

    /**
     * 个人心愿海（树洞）展示
     * @param uid
     * @param model
     * @param request
     * @return
     */
    @GetMapping(value = "/user/personalOcean/{uid}")
    public String showPersonalOcean(@PathVariable Integer uid,Model model,HttpServletRequest request)
    {
        System.out.println(uid);
        //当前登录的用户
        User cookieUser = (User) request.getSession().getAttribute("cookieUser");
        //要访问的用户
        User curUser=userService.queryById(uid);
        Integer offset=0;
        Integer limit=65535;
        List<Bottle> bottles=bottleService.queryByAuthor(uid,offset,limit);
        List<Integer> bids = new ArrayList<>();
        for(Bottle bottle: bottles)
        {
            //只显示公有的心愿瓶
            if(bottle.getIsPrivate().equals("false")){
                bids.add(bottle.getBid());
            }
        }
        System.out.println("public bottle size"+bids.size());
        model.addAttribute("cookieUser", cookieUser);
        model.addAttribute("bids", bids);
        model.addAttribute("curUser",curUser);
        return "user/personalOcean";
    }
}
