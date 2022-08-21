package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.ESAggMetricsOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ESAggMetricsOrderTest {
    @Autowired
    ESAggMetricsOrder metricsOrder;
    @Test
    void testMetricsOrderTest(){
        metricsOrder.getAggMetricsOrderSearch();
    }
}
