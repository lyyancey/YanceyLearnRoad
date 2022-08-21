package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.ESAggTopHits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ESAggTopHitsTest {
    @Autowired
    ESAggTopHits esAggTopHits;
    @Test
    void testAggTopHits(){
        esAggTopHits.getAggTopHitSearch();
    }
    @Test
    void testCollapseAgg(){
        esAggTopHits.getCollapseAggSearch();
    }
}
