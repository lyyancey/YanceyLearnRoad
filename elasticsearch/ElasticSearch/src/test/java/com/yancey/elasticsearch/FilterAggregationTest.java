package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsFilterAggregation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilterAggregationTest {
    @Autowired
    EsFilterAggregation aggregation;
    @Test
    void testFilterAggregation(){
        aggregation.getFilterAggSearch();
    }
}
