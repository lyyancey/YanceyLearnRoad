package com.yancey.elasticsearch.Service;

import com.yancey.elasticsearch.pojo.Hotel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EsService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    EsRepository esRepository;
    public List<Hotel> getHotelFromTime(String keyword){
        SearchRequest searchRequest = new SearchRequest("hotel"); // 客户端请求
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); // 构建query
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", keyword));
        searchRequest.source(searchSourceBuilder);
        List<Hotel> resultList = new ArrayList<Hotel>();
        try{
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            RestStatus status = searchResponse.status();
            if(status != RestStatus.OK){
                return null;
            }
            SearchHits searchHits=searchResponse.getHits();
            for(SearchHit searchHit : searchHits){
                Hotel hotel = new Hotel();
                hotel.setId(searchHit.getId());
                hotel.setIndex(searchHit.getIndex());
                hotel.setScore(searchHit.getScore());
                //转换为Map
                Map<String, Object> dataMap = searchHit.getSourceAsMap();
                hotel.setTitle((String) dataMap.get("title")); //设置标题
                hotel.setCity((String)dataMap.get("city")); // 设置城市
                hotel.setPrice((Double) dataMap.get("price")); //设置价格
                resultList.add(hotel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
    public List<Hotel> getHotelFromTitle(String keyword){
        return esRepository.findByTitleLike(keyword);
    }

    // 单条插入数据
    public void singleIndexDoc(Map<String, Object> dataMap, String indexName, String indexId){
        // 构建IndexRequest对象并设置对应的索引和_id字段名称
        IndexRequest indexRequest = new IndexRequest(indexName).id(indexId).source(dataMap);
        //执行写入
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            // 通过IndexResponse获取索引名
            String index = indexResponse.getIndex();
            // 通过indexResponse获取文档ID
            String id = indexResponse.getId();
            Long version = indexResponse.getVersion();
            System.out.println("index = "+index+", id = "+id+", version = "+version);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 批量插入数据
    public void bulkIndexDoc(String indexName, String docIdKey, List<Map<String, Object>> recordMapList){
        // 构建批量操作BulkRequest对象
        BulkRequest bulkRequest = new BulkRequest(indexName);
        for(Map<String, Object> dataMap : recordMapList){ // 遍历数据
            // 获取主键作为ES索引的主键
            String docId = dataMap.get(docIdKey).toString();
            // 构建IndexRequest对象
            IndexRequest indexRequest = new IndexRequest().id(docId).source(dataMap);
            // 添加IndexRequest
            bulkRequest.add(indexRequest);
        }
        // 设置超时时间
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));
        try {
            // 执行批量写入
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            // 判断执行状态
            if(bulkResponse.hasFailures()){
                System.out.println("bulk fail, message: "+bulkResponse.buildFailureMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void singleUpdate(String indexName, String docIdKey, Map<String, Object> recordMap){
        UpdateRequest updateRequest = new UpdateRequest(indexName, docIdKey);
        updateRequest.doc(recordMap);
        try{
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            // 通过indexResponse获取索引名成
            String index = updateResponse.getIndex();
            // 通过indexResponse获取文档ID
            String id = updateResponse.getId();
            // 通过indexResponse 获取文档版本
            Long version = updateResponse.getVersion();
            System.out.println("index = "+index+" id = "+id+" version = "+version);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void singleUpsert(String index, String docIdKey, Map<String, Object> recordMap, Map<String, Object> upRecordMap){
        // 构建UpdateRequest
        UpdateRequest updateRequest = new UpdateRequest(index, docIdKey);
        updateRequest.doc(recordMap); // 设置更新逻辑
        updateRequest.upsert(upRecordMap);// 设置插入逻辑
        try{
            //执行upsert命令
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void bulkUpdate(String index, String docIdKey, List<Map<String, Object>> recordMapList){
        BulkRequest bulkRequest = new BulkRequest(); // 构建BulkRequest对象
        for (Map<String, Object> dataMap : recordMapList){ // 遍历数据列表
            String docId = dataMap.get(docIdKey).toString();
            dataMap.remove(docIdKey); //将ID字段从map中删除
            // 创建UpdateRequest 对象
            bulkRequest.add(new UpdateRequest(index, docId).doc(dataMap));
            try{
                //执行批量更新
                BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                // 判断执行状态
                if(bulkResponse.hasFailures()){
                    System.out.println("bulk fail, message: " + bulkResponse.buildFailureMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void updateCityByQuery(String index, String oldCity, String newCity){
        // 构建UpdateByQueryRequest对象
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(index);
        //设置按照城市查找文档的query，如果更改所有文档中的某个字段， 下面这个query子句就可以不定义
        updateByQueryRequest.setQuery(new TermQueryBuilder("city.keyword", oldCity));
        // 设置更新城市字段的脚本逻辑
        updateByQueryRequest.setScript(new Script("ctx._source['city']='"+newCity+"';"));
        try{
            // 执行更新
            restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void singleDelete(String index, String docId){
        // 构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(index, docId);
        try{
            // 执行删除
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void bulkDelete(String index, List<String> docLdList){
        // 构建BulkRequest对象
        BulkRequest bulkRequest = new BulkRequest();
        for(String docId : docLdList){
            // 构建删除请求
            DeleteRequest deleteRequest = new DeleteRequest(index, docId);
            bulkRequest.add(deleteRequest);
        }
        try{
            // 执行批量删除
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(bulkResponse.hasFailures()){// 判断执行状态
                System.out.println("bulk fail, message: "+ bulkResponse.buildFailureMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteByQuery(String index, String city){
        // 构建DeleteByQueryRequest对象
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(index);
        // 设置按照城市查找文档的Query
        deleteByQueryRequest.setQuery(new TermQueryBuilder("city.keyword", city));
        try{
            // 执行删除命令
            restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
