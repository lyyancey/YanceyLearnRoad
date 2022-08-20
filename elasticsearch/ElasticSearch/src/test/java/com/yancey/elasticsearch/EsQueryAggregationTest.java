package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.QueryAggregation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EsQueryAggregationTest {
    @Autowired
    QueryAggregation queryAggregation;
    @Test
    void testQueryAggregation(){
        queryAggregation.getQueryAggSearch();
    }
}
