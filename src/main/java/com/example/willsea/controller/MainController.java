package com.example.willsea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yt on 2018/6/23.
 */
@Controller
public class MainController {

    @GetMapping(value = "")
    public String index(Model model) {
        return "index";
    }
}
