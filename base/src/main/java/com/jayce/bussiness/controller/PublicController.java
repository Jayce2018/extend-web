package com.jayce.bussiness.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/public")
public class PublicController {

    @ResponseBody
    @RequestMapping(value = "/input")
    public JSONObject input(@RequestParam String input) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("baseInput",input);
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/ping")
    public JSONObject ping() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("basePing",200);
        return jsonObject;
    }
}
