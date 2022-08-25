package com.yancey.hotelsearchweb.service;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.spatial3d.geom.GeoPoint;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessDataService {
    @Autowired
    RestHighLevelClient client;
    public void startInit(){
        // 从文件中获取酒店信息列表
        List<String> hotelList = getListFromFile("hotel.txt");
        List<Map<String, Object>> recordMapList = new ArrayList<>();
        // 遍历酒店列表
        for(String hotelLine : hotelList){
            Map<String, Object> dataMap = processHotelData(hotelLine);
            if(dataMap.size() == 0){
                continue;
            }
            // 将酒店信息添加到列表中
            recordMapList.add(dataMap);
        }
        //将酒店信息批量添加到索引中
        bulkIndexDoc("hotel", "id", recordMapList);
    }

    private void bulkIndexDoc(String index, String id, List<Map<String, Object>> recordMapList) {
        // 构建批量操作BulkRequest
        BulkRequest request = new BulkRequest(index);
        for(Map<String, Object> hotelInfo : recordMapList){
            String docId = hotelInfo.get(id).toString();
            IndexRequest indexRequest = new IndexRequest().id(docId).source(hotelInfo);
            request.add(indexRequest);
        }
        try {
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
            if(response.hasFailures()){
                System.out.println("bulk failures message" + response.buildFailureMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Map<String, Object> processHotelData(String hotelLine) {
        String[] wordList = new String[]{"id", "title", "business_district",
                "address", "location", "city", "price", "star", "full_room", "impression", "favourable_percent", "pic"};
        String[] hotelInfo = hotelLine.split("#");
        Map<String, Object> result = new HashMap<>();
        if(wordList.length != hotelInfo.length){
            System.out.println(hotelInfo[0]);
            return result;
        }
        for(int i=0; i<wordList.length; i++){
/*            if(wordList[i].equals("location")){
                String[] geoPoint = hotelInfo[i].split(",");
                result.put(wordList[i], new GeoPoint(Double.valueOf(geoPoint[0]), Double.valueOf(geoPoint[1])));
                continue;
            }*/
            result.put(wordList[i], hotelInfo[i]);
        }
        return result;
    }

    private List<String> getListFromFile(String s) {
        List<String> hotelList = new ArrayList<>();
        try{
            byte[] bytes = null;
            ClassPathResource resource = new ClassPathResource(s);
            InputStream inputStream = resource.getInputStream();
            bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            InputStreamReader input = new InputStreamReader(byteArrayInputStream);
            BufferedReader reader = new BufferedReader(input);
            String line = null;
            while((line = reader.readLine())!=null){
                hotelList.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return hotelList;
    }
}
