package com.yancey.elasticsearch.controller;

import com.yancey.elasticsearch.Service.EsService;
import com.yancey.elasticsearch.pojo.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    EsService esService;
    @RequestMapping("/test")
    public String getRec() throws Exception{
        List<Hotel> hotelList = esService.getHotelFromTime("再来");
        if(hotelList!=null && hotelList.size()>0){
            return hotelList.toString();
        }else{
            return "no data";
        }
    }
    @RequestMapping("/hotel")
    public String getHotelByTitle() throws Exception{
        List<Hotel> hotelList = esService.getHotelFromTitle("再来");
        if(hotelList!=null && hotelList.size()>0){
            return hotelList.toString();
        }else{
            return "no data";
        }
    }
}
