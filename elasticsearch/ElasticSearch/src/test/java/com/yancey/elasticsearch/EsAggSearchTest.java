package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsAggreSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
