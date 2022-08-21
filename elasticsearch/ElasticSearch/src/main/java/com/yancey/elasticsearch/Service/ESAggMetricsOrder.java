package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESAggMetricsOrder {
    @Autowired
    RestHighLevelClient client;

    public void getAggMetricsOrderSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String termsAggName = "my_terms"; // 聚合名称
        // 定义terms聚合，指定字段为城市
        String avgAggName = "my_avg"; // avg聚合名称
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(termsAggName).field("city");
        BucketOrder bucketOrder = BucketOrder.aggregation(avgAggName, true);
        termsAggregationBuilder.order(bucketOrder);

        // 定义sum聚合，指定字段为价格
        AvgAggregationBuilder avgAgg = AggregationBuilders.avg(avgAggName).field("price");
        // 定义聚合的父子关系
        termsAggregationBuilder.subAggregation(avgAgg);
        builder.aggregation(termsAggregationBuilder);// 添加聚合
        request.source(builder); // 设置查询请求
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT); // 执行搜索
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(termsAggName);// 获取聚合返回的对象
            for(Terms.Bucket bucket : terms.getBuckets()){
                String bucketKey = bucket.getKeyAsString();
                System.out.println("termsKey = " + bucketKey);
                Avg avg = bucket.getAggregations().get(avgAggName);
                String key = avg.getName(); // 聚合名称
                double avgVal = avg.getValue();
                System.out.println("key="+key+", avgVal="+avgVal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
