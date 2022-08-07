package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsAggreSearch {
    @Autowired
    private RestHighLevelClient client;
    public void getAvgAggSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String aggName = "my_agg";
        // 定义avg聚合， 指定字段为price
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg(aggName).field("price");
        sourceBuilder.aggregation(avgAggregationBuilder);// 添加聚合
        searchRequest.source(sourceBuilder); // 设置查询请求

        try {
            // 执行查询
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取avg聚合返回的对象
            Avg avg = aggregations.get(aggName);
            // 获取聚合名称
            String key = avg.getName();
            //获取聚合值
            double avgValue = avg.getValue();
            // 打印结果
            System.out.println("key = "+key+" avgValue = "+avgValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void getStatsAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 聚合名称
        String aggName = "my_agg";
        // 定义stats聚合，指定字段为price
        StatsAggregationBuilder aggregationBuilders = AggregationBuilders.stats(aggName).field("price");
        // 添加聚合
        sourceBuilder.aggregation(aggregationBuilders);
        request.source(sourceBuilder);//设置查询请求
        try {
            // 执行查询
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取stats聚合返回的对象
            Stats stats = aggregations.get(aggName);
            String key = stats.getName(); // 获取聚合名称
            double sumVal = stats.getSum();// 获取聚合加和值
            double avgVal = stats.getAvg(); // 获取聚合平均值
            long contVal = stats.getCount();// 获取聚合文档数量值
            double maxVal = stats.getMax(); // 获取聚合最大值
            double minVal = stats.getMin(); // 获取聚合最小值
            System.out.println("key = "+key);
            System.out.println("sumVal="+sumVal+" avgVal = "+avgVal+" countVal = "+contVal+ " maxVal="+maxVal+" minVal = "+minVal);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getValueCountAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String aggName = "my_agg"; // 聚合名称
        // 定义value_count聚合，指定字段为price
        ValueCountAggregationBuilder aggregationBuilder = AggregationBuilders.count(aggName).field("price");
        aggregationBuilder.missing("200");
        sourceBuilder.aggregation(aggregationBuilder);// 添加聚合
        request.source(sourceBuilder); // 设置查询请求
        try {
            // 执行查询
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取value_count聚合返回的对象
            ValueCount valueCount = aggregations.get(aggName);
            String key = valueCount.getName(); // 获取聚合名称
            long count = valueCount.getValue(); // 获取聚合值
            System.out.println("key = "+ key + ", count = "+count);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
