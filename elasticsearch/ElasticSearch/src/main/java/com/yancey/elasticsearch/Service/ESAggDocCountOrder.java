package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESAggDocCountOrder {
    @Autowired
    RestHighLevelClient client;

    public void getAggDocCountOrderSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String termsAggName = "my_terms";
        // 定义terms聚合,指定字段为城市
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(termsAggName).field("city");
        BucketOrder bucketOrder = BucketOrder.count(true);
        termsAggregationBuilder.order(bucketOrder);
        String avgAggName = "my_avg"; // avg聚合的名称
        // 定义sum聚合，指定字段为价格
        SumAggregationBuilder avgAgg = AggregationBuilders.sum(avgAggName).field("price");
        // 定义聚合的父子关系
        termsAggregationBuilder.subAggregation(avgAgg);
        builder.aggregation(termsAggregationBuilder);// 添加聚合
        request.source(builder); // 设置查询请求

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits(); // 获取搜索结果
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(termsAggName); // 获取聚合返回的对象
            for(Terms.Bucket bucket : terms.getBuckets()){
                String bucketKey = bucket.getKeyAsString();
                System.out.println("termsKey="+bucketKey);
                Sum sum  = bucket.getAggregations().get(avgAggName);
                String key = sum.getName();// 获取聚合名称
                double sumVal = sum.getValue(); // 获取聚合名称
                System.out.println("key = "+key+", count="+sumVal);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
