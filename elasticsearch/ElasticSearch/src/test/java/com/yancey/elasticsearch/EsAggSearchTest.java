package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsAggreSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EsAggSearchTest {
    @Autowired
    private EsAggreSearch aggreSearch;
    @Test
    void testGetAvgAggSearch(){
        aggreSearch.getAvgAggSearch();
    }
    @Test
    void testGetStatsAggSearch(){
        aggreSearch.getStatsAggSearch();
    }

    @Test
    void testGetValueCountAggSearch(){
        aggreSearch.getValueCountAggSearch();
    }
    @Test
    void testSumAgg(){
        aggreSearch.getSumAggSearch();
    }
    @Test
    void testBucketDocCount(){
        aggreSearch.getBucketDocCountAggSearch();
    }
    @Test
    void testRangeDocCountAggSearch(){
        aggreSearch.getRangeDocCountAggSearch();
    }
    @Test
    void testBucketAggSearch(){
        aggreSearch.getBucketAggSearch();
    }
    @Test
    void testExternalBucketAggSearch(){
        aggreSearch.getExternalBucketAggSearch();
    }
    @Test
    void testGeoDistanceAgg(){
        aggreSearch.getGeoDistanceAggSearch();
    }
}
