package com.jayce.bussiness.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jayce.bussiness.controller.PublicController;

@Controller
@RequestMapping(value = "/public")
public class StOnePublicController extends PublicController{

    @Override
    @ResponseBody
    @RequestMapping(value = "/input")
    public JSONObject input(@RequestParam String input) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("inputStable1",input);
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/design")
    public JSONObject design(@RequestParam String design) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("inputStable1",design);
        return jsonObject;
    }
}
