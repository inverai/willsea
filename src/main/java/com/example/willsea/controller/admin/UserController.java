package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.User;
import com.example.willsea.exception.SubException;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.IUserService;
import com.sun.deploy.net.HttpResponse;
import jdk.internal.util.xml.impl.Input;
import org.apache.ibatis.annotations.Param;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    public  String showSignIn() {
        return "/user/sign";
    }

    @PostMapping(value = "/user/submit")
    public String signInPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user= userService.queryToVerify(username,password);
        if(user != null) {
            System.out.println(user.getUid());
            // 1.ModelAndView
            // 2. redirect
            // 3.
//            RestResponse.ok(user.getUid());
           return "redirect:/user/usercenter/" + user.getUid();
        }
//        return RestResponse.fail();
        return "/error/500";
    }
    @GetMapping(value = "/user/usercenter/{uid}")    //用户中心控制器
    public String showUserCenter(@PathVariable Integer uid, Model model) {
            User user=userService.queryById(uid);
            System.out.println("into usercenter");
            Integer pageLimit=9;
            Integer offset=(1-1)*pageLimit;
            List<Bottle>  bottles=bottleService.queryByAuthor(user.getUid(),offset,pageLimit);
            List<User>  favoriteList=userService.queryFavoriteList(user);
            List<User>  blackList=userService.queryBlackList(user);
            model.addAttribute("user",user);
            model.addAttribute("bottles",bottles);
            model.addAttribute("favoriteList",favoriteList);
            model.addAttribute("blackList",blackList);
            return "/user/usercenter";
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
        System.out.println("excute register");
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


    @PostMapping(value = "/user/modifyInfo")
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
        System.out.println("modify username successfully.");
        return RestResponse.ok("modify successfully.");
    }


    @PostMapping(value = "/user/modifyPassword")
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


    @PostMapping(value = "/user/uploadFile")
    public String uploadFiles(HttpServletRequest request, HttpServletResponse response){
        HashMap<String, String> hashMap = new HashMap<>();
        System.out.println("into upload");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File tempDirectory = new File("E:\\WEB\\springBoot");
        factory.setRepository(tempDirectory);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        System.out.println("parse request");
        // parse request
        upload.setFileSizeMax(1024*1024*1000);

        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item: items) {
                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString();

                    hashMap.put(name, value);
                    System.out.println("parse data" + name + value);
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getString();

                    InputStream in = item.getInputStream();
                    byte[] buffer = new byte[4096];
                    int len = 0;

                    fileName = "E:\\WEB\\springBoot\\willsea\\src\\main\\resources\\static\\user\\" + hashMap.get("uid") + "\\" + fileName;
                    System.out.println(fileName);
                    hashMap.put(fieldName,fileName);
                    OutputStream out = new FileOutputStream(fileName);
                    while((len = in.read(buffer)) != -1){
                        out.write(buffer,0, len);
                    }

                    out.close();
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bottle bottle = new Bottle();
        bottle.setAid(Integer.valueOf(hashMap.get("uid")));
        bottle.setTitle(hashMap.get("myTitle"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        bottle.setTime(date);
        bottle.setBtext(hashMap.get("myText"));
        bottle.setBaudio(hashMap.get("myAudio"));
        bottle.setBvideo(hashMap.get("myVideo"));
        if(hashMap.get("myPrivate").equals("checked")){
            bottle.setIsPrivate("true");
        } else {
            bottle.setIsPrivate("false");
        }

        return "upload success";
    }


    @PostMapping(value = "/user/upload")
    public String upload(HttpServletRequest request, HttpServletResponse response){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        HashMap<String, String> map = new HashMap<>();
        List<String> audios = new ArrayList<String>();
        List<String> videos = new ArrayList<>();
        audios.add("mp3");
        audios.add("wav");
        audios.add("midi");
        videos.add("mp4");
        videos.add("avi");
        videos.add("wma");
        videos.add("flash");
        try {
            Iterator<String> iterator = req.getFileNames();
            String uid = req.getParameter("uid");
            System.out.println(uid);
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileName = file.getOriginalFilename();
                if(!fileName.equals("")){
                    int split = fileName.lastIndexOf(".");

                    byte[] bytes = file.getBytes();


                    String root = System.getProperty("user.dir");
                    String resourcePath = root + "\\src\\main\\resources\\static\\user\\";
                    System.out.println("real root path: " + root);
                    File direct = new File(resourcePath + uid);
                    if(!direct.exists()){
                        direct.mkdir();
                    }
                    fileName = resourcePath + uid + "\\" + fileName;
                    System.out.println(fileName);
                    File temp = new File(fileName);
                    OutputStream out = new FileOutputStream(temp);

                    out.write(bytes, 0, bytes.length);
                    out.close();

                    int splitPath = fileName.lastIndexOf("user");
                    String format = fileName.substring(splitPath - 1,fileName.length());
                    if(audios.contains(format)){
                        map.put("audio", format);
                    } else {
                        map.put("video", format);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("warning in access");
        }

        Bottle bottle = new Bottle();
        bottle.setAid(Integer.valueOf(req.getParameter("uid")));
        bottle.setTitle(req.getParameter("myTitle"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        bottle.setTime(date);
        bottle.setBtext(req.getParameter("myText"));
        bottle.setBaudio(map.get("audio"));
        bottle.setBvideo(map.get("video"));
        if(req.getParameter("myPrivate") == null){
            bottle.setIsPrivate("false");
        }else {
            bottle.setIsPrivate("true");
        }

        bottleService.createBottle(bottle);
        return "/user/usercenter/" + req.getParameter("uid");
    }


    private  void clearPrivateMessage(User user) {
        user.setPassword("");
        user.setEmail("");
        user.setTelephone("1");
        user.setLocation("");
    }

}

// 1. 保持登陆状态，可以注销；
// 2. 漂流瓶的创建功能；（文字的存储，媒体文件的存储）
// 3. 评论的创建功能；
