package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsQueryService {
    @Autowired
    RestHighLevelClient client;
    public long getCityCount(String index, String cityName){
        CountRequest countRequest = new CountRequest(index);
        // 构建Query
        countRequest.query(new TermQueryBuilder("city", cityName));
        try{
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
            return countResponse.getCount();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1l;
    }

    public List<String> matchAllSearch(String index){
        // 新建搜索请求
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建match_all 查询,并设置boost的值为2.0
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery().boost(2.0f);
        searchSourceBuilder.query(matchAllQueryBuilder);
        searchRequest.source(searchSourceBuilder);// 设置查询
        List<String> list = null;
        try {
            // 执行搜索
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            // 获取搜索结果
            SearchHits searchHits = searchResponse.getHits();
            list = getQueryResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> mustSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel002"); // 新建请求
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", "北京");
        // 构建价格的range查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(300).lte(400);
        // 进行关系“与”查询
        boolQueryBuilder.must(termQueryBuilder).must(rangeQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);// 设置查询
        List<String> list = null;
        try {
            // 执行搜索
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            // 获取搜索结果
            SearchHits searchHits = searchResponse.getHits();
            list = getQueryResult(searchHits);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    private List<String> getQueryResult(SearchHits searchHits){
        List<String> list = new ArrayList<>();
        for (SearchHit searchHit : searchHits){
            StringBuilder builder = new StringBuilder();
            builder.append("index = ");
            builder.append(searchHit.getIndex());
            builder.append(" , id = ");
            builder.append(searchHit.getId());
            builder.append(" score = ");
            builder.append(searchHit.getScore());
            builder.append(" source = ");
            builder.append(searchHit.getSourceAsString());
            list.add(builder.toString());
        }
        return list;
    }
    public List<String> shouldSearch(){
        // 新建请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 构建城市为”北京“的term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", "北京");
        //构建城市为"天津"的term查询
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("city", "天津");
        // 进行关系should查询
        boolQueryBuilder.should(termQueryBuilder).should(termQueryBuilder1);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getQueryResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> mustNotSearch(){
        // 新建搜索请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 构建城市为北京的term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", "北京");
        // 构建城市为天津的term查询
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("city", "天津");
        // 进行关系为must_not查询
        boolQueryBuilder.mustNot(termQueryBuilder).mustNot(termQueryBuilder1);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse  = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getQueryResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> filterSearch(){
         SearchRequest searchRequest = new SearchRequest("hotel002");
         SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
         boolQueryBuilder.filter(QueryBuilders.termQuery("city", "北京"));
         boolQueryBuilder.filter(QueryBuilders.termQuery("full_room", false));
         searchSourceBuilder.query(boolQueryBuilder);
         searchRequest.source(searchSourceBuilder);
         List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getQueryResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
