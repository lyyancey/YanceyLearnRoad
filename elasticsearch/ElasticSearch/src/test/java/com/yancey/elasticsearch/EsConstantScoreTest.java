package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsConstantScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class EsConstantScoreTest {
    @Autowired
    EsConstantScore esConstantScore;

    @Test
    void testConstantScore(){
        List<String> list = esConstantScore.constantScoreSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testFunctionScoreSearch(){
        List<String> list = esConstantScore.functionScoreSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testMatchSearch(){
        List<String> list = esConstantScore.matchSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testMutiMatchSearch(){
        List<String> list = esConstantScore.mutiMatchSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testMatchPhraseSearch(){
        List<String> list = esConstantScore.matchPhraseSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testGeoDistanceSearch(){
        List<String> list = esConstantScore.geoDistanceSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testGeoPolygonSearch(){
        List<String> list = esConstantScore.geoPolygonSearch();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testSuggestSearch(){
        esConstantScore.suggestSearch();
    }
    @Test
    void testCommonSort(){
        List<String> list = esConstantScore.commonSort();
        for(String s : list) {
            System.out.println(s);
        }
    }
    @Test
    void testGeoDistanceSearchSort(){
        try {
            esConstantScore.geoDistanceSearchSort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
