package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.ESAggDocCountOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EsAggDocCountOrderTest {
    @Autowired
    ESAggDocCountOrder countOrder;
    @Test
    void testAggDocCountOrderTest(){
        countOrder.getAggDocCountOrderSearch();
    }
}
