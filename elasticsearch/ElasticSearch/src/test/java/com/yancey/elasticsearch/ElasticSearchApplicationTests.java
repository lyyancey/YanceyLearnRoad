package com.yancey.elasticsearch;

import com.yancey.elasticsearch.Service.EsService;
import com.yancey.elasticsearch.pojo.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ElasticSearchApplicationTests {
    @Autowired
    EsService esService;

    @Test
    void testSingleMethod() {
        Map<String, Object> map = new HashMap<>();
        map.put("city", "山东");
        map.put("score", 11.0f);
        map.put("title", "青云酒店");
        map.put("index", "001");
        map.put("id", "001");
        map.put("price", "123.0");
        esService.singleIndexDoc(map, "hotel001", "1");
    }
    @Test
    void testBulkMethod(){
        Map<String, Object> map0 = new HashMap<>();
        map0.put("city", "德州");
        map0.put("score", 112.0f);
        map0.put("title", "麦肯锡酒店");
        map0.put("index", "002");
        map0.put("id", "002");
        map0.put("price", "125.0");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("city", "青岛");
        map1.put("score", 11.0f);
        map1.put("title", "浩辉酒店");
        map1.put("index", "003");
        map1.put("id", "003");
        map1.put("price", "125.0");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("city", "淄博");
        map2.put("score", 114.0f);
        map2.put("title", "青克酒店");
        map2.put("index", "004");
        map2.put("id", "004");
        map2.put("price", "1223.0");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("city", "威海");
        map3.put("score", 119.0f);
        map3.put("title", "大顺酒店");
        map3.put("index", "005");
        map3.put("id", "005");
        map3.put("price", "145.0");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map0);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        esService.bulkIndexDoc("hotel001", "id", list);
    }
    @Test
    void testUpdateSingleMethod(){
        Map<String, Object> map = new HashMap<>();
        map.put("city", "莱芜");
        map.put("score", 1111.0f);
        map.put("title", "青云酒店");
        map.put("index", "001");
        map.put("id", "001");
        map.put("price", "123.0");
        esService.singleUpdate("hotel001", "1", map);
    }

    @Test
    void testUpsertMethod(){
        Map<String, Object> map = new HashMap<>();
        map.put("city", "莱芜");
        map.put("score", 1111.0f);
        map.put("title", "青海酒店");
        map.put("index", "001");
        map.put("id", "001");
        map.put("price", "123.0");

        Map<String, Object> map0 = new HashMap<>();
        map0.put("city", "青州");
        map0.put("score", 112.0f);
        map0.put("title", "骇客酒店");
        map0.put("index", "009");
        map0.put("id", "009");
        map0.put("price", "127.0");

        esService.singleUpsert("hotel001", "007", map, map0);
    }

    @Test
    void testBulkUpdate(){
        Map<String, Object> map0 = new HashMap<>();
        map0.put("city", "德州");
        map0.put("score", 111.0f);
        map0.put("title", "麦肯锡酒店");
        map0.put("index", "002");
        map0.put("id", "002");
        map0.put("price", "125.0");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("city", "青岛");
        map1.put("score", 112.0f);
        map1.put("title", "浩辉酒店");
        map1.put("index", "003");
        map1.put("id", "003");
        map1.put("price", "125.0");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("city", "淄博");
        map2.put("score", 116.0f);
        map2.put("title", "青克酒店");
        map2.put("index", "004");
        map2.put("id", "004");
        map2.put("price", "1223.0");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("city", "威海");
        map3.put("score", 119.0f);
        map3.put("title", "大顺酒店");
        map3.put("index", "005");
        map3.put("id", "005");
        map3.put("price", "145.0");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map0);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        esService.bulkUpdate("hotel001", "id", list);
    }
    @Test
    void testUpdateCityByQuery(){
        esService.updateCityByQuery("hotel001", "烟台","聊城");
    }
    @Test
    void testDeleteSingleDelete(){
        esService.singleDelete("hotel001", "004");
    }
    @Test
    void deleteBulkDelete(){
        List<String> list = new ArrayList<>();
        list.add("005");
        list.add("004");
        esService.bulkDelete("hotel001", list);
    }
    @Test
    void testDeleteByQuery(){
        esService.deleteByQuery("hotel001", "青岛");
    }
}
