package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.AggregationKeyOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EsAggKeyOrderTest {
    @Autowired
    AggregationKeyOrder keyOrder;
    @Test
    void testAggKeyOrder(){
        keyOrder.getAggKeyOrderSearch();
    }
}
