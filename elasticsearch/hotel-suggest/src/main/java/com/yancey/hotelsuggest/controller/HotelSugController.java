package com.yancey.hotelsuggest.controller;

import com.alibaba.fastjson.JSONObject;
import com.yancey.hotelsuggest.dto.SugReq;
import com.yancey.hotelsuggest.service.PyProcessService;
import com.yancey.hotelsuggest.service.SugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HotelSugController {
    @Autowired
    PyProcessService pyProcessService;
    @Autowired
    SugService sugService;
    @PostMapping(value = "/sug", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRec(@RequestBody SugReq sugReq) throws Exception {
        List<String> sugList = sugService.suggestSearch(sugReq.getProfixWord());
        JSONObject result = new JSONObject();
        result.put("resultId", sugReq.getRequestId());
        result.put("subList", sugList);
        return result.toJSONString();
    }
    @GetMapping("/sug-init-index")
    public String initIndex() throws Exception {
        pyProcessService.startProcess();
        return "process sucess";
    }
}
