package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ESQueryServiceTest {
    @Autowired
    EsQueryService esQueryService;

    @Test
    void testGetCityCount(){
        Long cityNum = esQueryService.getCityCount("hotel002", "北京");
        System.out.println(cityNum);
    }
    @Test
    void testMatchAllSearch(){
        List<String> list = esQueryService.matchAllSearch("hotel002");
        for(String s : list){
            System.out.println(s);
        }

    }
    @Test
    void testMustBoolSearch(){
        List<String> list = esQueryService.mustSearch();
        for(String s : list){
            System.out.println(s);
        }
    }
    @Test
    void testShouldBoolSearch(){
        List<String> list = esQueryService.shouldSearch();
        for(String s : list){
            System.out.println(s);
        }
    }
    @Test
    void testMustNotSearch(){
        List<String> list = esQueryService.mustNotSearch();
        for(String s : list){
            System.out.println(s);
        }
    }
    @Test
    void testFilterSearch(){
        List<String> list = esQueryService.filterSearch();
        for(String s : list){
            System.out.println(s);
        }
    }
}
