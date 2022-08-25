package com.yancey.hotelsearchweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.yancey.hotelsearchweb.dto.HotelSearchResult;
import com.yancey.hotelsearchweb.dto.request.HotelSearchRequest;
import com.yancey.hotelsearchweb.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class SearchWebController {
    @Autowired
    HotelSearchService hotelSearchService;
    @PostMapping(value = "/start-search", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String startSearch(@RequestBody HotelSearchRequest hotelSearchRequest) throws Exception {
        JSONObject result = new JSONObject();
        HotelSearchResult hotelSearchResult = hotelSearchService.getCommonSearch(hotelSearchRequest);
        result.put("requestId", hotelSearchResult.getRequestId());
        result.put("searchResult", hotelSearchResult);
        return result.toJSONString();

    }
}
