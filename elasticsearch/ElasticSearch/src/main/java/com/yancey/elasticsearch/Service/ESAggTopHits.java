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
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESAggTopHits {
    @Autowired
    RestHighLevelClient client;

    public void getAggTopHitSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //聚合名称
        String termsAggName = "my_terms";
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(termsAggName).field("city");
        BucketOrder bucketOrder = BucketOrder.key(true);
        termsAggregationBuilder.order(bucketOrder);

        String topHitsAggName = "my_top"; // 聚合名称
        TopHitsAggregationBuilder topHitsAgg = AggregationBuilders.topHits(topHitsAggName);
        topHitsAgg.size(3);
        // 定义聚合的父子关系
        termsAggregationBuilder.subAggregation(topHitsAgg);
        // 添加聚合
        builder.aggregation(termsAggregationBuilder);
        builder.query(QueryBuilders.matchQuery("title", "金都"));
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取sum聚合返回的对象
            Terms terms = aggregations.get(termsAggName);
            for(Terms.Bucket bucket : terms.getBuckets()){
                String bucketKey = bucket.getKeyAsString();
                System.out.println("termsKey = "+bucketKey);
                TopHits topHits = bucket.getAggregations().get(topHitsAggName);
                SearchHit[] searchHits = topHits.getHits().getHits();
                for(SearchHit searchHit : searchHits){
                    System.out.println(searchHit.getSourceAsString());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCollapseAggSearch(){
        // 按照sup进行分组
        //按照城市进行分组
        CollapseBuilder collapseBuilder = new CollapseBuilder("city");
        SearchRequest request = new SearchRequest("hotel003");// 新建搜索请求
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 新建match查询
        builder.query(QueryBuilders.matchQuery("title", "金都"));
        builder.collapse(collapseBuilder); // 设置Collapse聚合
        request.source(builder); //设置查询
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);// 执行搜索
            SearchHits searchHits = response.getHits();// 获取搜索结果集
            for(SearchHit searchHit : searchHits){
                String index = searchHit.getIndex(); // 获取索引名称
                String id = searchHit.getId(); // 获取文档id
                Float score = searchHit.getScore();// 获取得分
                String source = searchHit.getSourceAsString();//获取文档内容
                System.out.println("index = "+index+", id = "+id+", score = "+score+", source = "+source);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
