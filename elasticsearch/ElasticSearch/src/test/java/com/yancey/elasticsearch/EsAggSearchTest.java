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
    void testMethod(){
        groupThePeople(new int[]{3,3,3,3,3,1,3});
    }

    List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        for(int val : groupSizes) {
            List<Integer> list = map.getOrDefault(String.valueOf(val), new ArrayList<>());
            if(list.size() == val) {
                res.add(list);
                map.remove(String.valueOf(val));
            }else {
                list.add(val);
            }
        }
        for(String val : map.keySet()){
            res.add(map.get(val));
        }
        return res;
    }
}
