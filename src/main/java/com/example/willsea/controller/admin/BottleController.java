package com.example.willsea.controller.admin;

import com.example.willsea.entity.Bottle;
import com.example.willsea.service.IBottleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/26.
 */
@Controller
@RequestMapping("/back")
public class BottleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BottleController.class);

    @Resource
    private IBottleService bottleService;

    @GetMapping(value = "/wishbottle")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "limit", defaultValue = "10") int limit, Model model){
        List<Bottle> bottles = bottleService.queryAll(page, limit);
        Integer recordNum = bottles.size();
        model.addAttribute("bottles", bottles);
        model.addAttribute("recordNum", recordNum);
        for (Bottle bottle: bottles) {
            System.out.println(bottle.getAid() + " ; " + bottle.getBid() + " ; " + bottle.getContent());
        }

        return "back/wishbottle";
    }
}
