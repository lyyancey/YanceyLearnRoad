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
public class AggregationKeyOrder {
    @Autowired
    RestHighLevelClient client;

    public void getAggKeyOrderSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String termAggName = "my_terms"; // 聚合名称
        // 定义terms聚合，指定字段为城市
        String avgAggName = "my_avg"; // avg聚合的名称
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(termAggName).field("city");
        BucketOrder bucketOrder = BucketOrder.key(true);
        termsAggregationBuilder.order(bucketOrder);

        // 定义sum聚合，指定字段为价格
        SumAggregationBuilder avgAgg = AggregationBuilders.sum(avgAggName).field("price");
        // 定义聚合的父子关系
        termsAggregationBuilder.subAggregation(avgAgg);
        builder.aggregation(termsAggregationBuilder); // 添加聚合
        request.source(builder); // 设置查询请求
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();// 获取搜索结果集
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(termAggName); // 获取sum聚合返回的对象
            for(Terms.Bucket bucket : terms.getBuckets()){
                String bucketKey = bucket.getKeyAsString();
                System.out.println("termsKey = "+bucketKey);
                Sum sum = bucket.getAggregations().get(avgAggName);
                String key = sum.getName(); // 获取聚合名称
                double sumVal = sum.getValue(); //获取聚合值
                System.out.println("key="+key+", count = "+sumVal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
