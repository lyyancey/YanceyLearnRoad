package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class QueryAggregation {
    @Autowired
    RestHighLevelClient client;
    public void getQueryAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String avgAggName = "my_agg"; // avg聚合的名称
        // 定义sum聚合，指定字段为价格
        AvgAggregationBuilder avgAgg = AggregationBuilders.avg(avgAggName).field("price");
        sourceBuilder.aggregation(avgAgg); // 添加聚合
        // 构建Query查询
        sourceBuilder.query(QueryBuilders.termQuery("city", "北京"));
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            // 执行搜索
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 获取搜索结果集
        SearchHits searchHits = response.getHits();
        System.out.println("--------------hit---------------");
        for(SearchHit searchHit : searchHits){ // 遍历搜索结果级
            String index = searchHit.getIndex();// 获取索引名称
            String id = searchHit.getId();// 获取文档Id
            Float score = searchHit.getScore();// 获取文档得分
            String source = searchHit.getSourceAsString(); //获取文档内容
            System.out.println("index="+index+", id="+id+", source="+source);
        }
        System.out.println("------------------------------------");
        // 获取聚合结果
        Aggregations aggregations = response.getAggregations();
        ParsedAvg avg = aggregations.get(avgAggName);// 获取聚合返回的对象
        String avgName = avg.getName();// 获取聚合名称
        double avgVal = avg.getValue(); // 获取聚合值
        // 打印结果
        System.out.println("avgName="+avgName+", avgVal="+avgVal);
    }
}
