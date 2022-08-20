package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.*;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
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
    public void getSumAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String aggName="my_agg";
        // 定义sum聚合，指定字段为price
        SumAggregationBuilder aggregationBuilder = AggregationBuilders
                .sum(aggName)
                .field("price");
        aggregationBuilder.missing("100");
        // 添加聚合
        builder.aggregation(aggregationBuilder);
        //设置查询请求
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取sum聚合返回的对象
            Sum sum = aggregations.get(aggName);
            String key = sum.getName();// 获取聚合名称
            double sumVal = sum.getValue(); //获取聚合值
            System.out.println("key="+key+", count="+sumVal);// 打印结果


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBucketDocCountAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 指定聚合名称
        String termsAggName = "my_terms";
        // 定义terms聚合，指定字段为城市
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                .terms(termsAggName)
                .field("full_room");
        sourceBuilder.aggregation(termsAggregationBuilder);// 添加聚合
        request.source(sourceBuilder);
        // 执行查询
        try {
            SearchResponse response = client.search(request,RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(termsAggName);// 获取聚合返回的对象
            for(Terms.Bucket bucket : terms.getBuckets()){
                String bucketKey = bucket.getKeyAsString();//获取桶名称
                long docCount = bucket.getDocCount(); // 获取文档个数
                System.out.println("termsKey="+ bucketKey+", docCount="+docCount);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getRangeDocCountAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String rangeAggName = "my_range";
        RangeAggregationBuilder rangeAgg = AggregationBuilders.range(rangeAggName).field("price");
        rangeAgg.addRange(new RangeAggregator.Range(null, null, 200d));
        rangeAgg.addRange(new RangeAggregator.Range(null, 200d, 500d));
        rangeAgg.addRange(new RangeAggregator.Range(null, 500d, null));
        builder.aggregation(rangeAgg);// 添加range聚合
        request.source(builder);// 设置查询请求
        // 执行查询
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 获取聚合结果
        Aggregations aggregations = response.getAggregations();
        // 获取range聚合返回的对象
        Range range = aggregations.get(rangeAggName);
        for(Range.Bucket bucket : range.getBuckets()){
            String bucketKey = bucket.getKeyAsString();// 获取桶名称
            long docCount = bucket.getDocCount(); // 获取聚合文档个数
            System.out.println("bucketKey="+bucketKey+", docCount="+docCount);
        }
    }
    public void getBucketAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder build = new SearchSourceBuilder();
        String termAggName = "my_terms"; // 聚合的名称
        // 定义terms聚合，指定字段为城市
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                .terms(termAggName)
                .field("city");
        String sumAggName = "my_sum"; // Sum聚合名称
        // 定义sum聚合， 指定字段为价格
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum(sumAggName).field("price");
        // 定义聚合的父子关系
        termsAggregationBuilder.subAggregation(sumAggregationBuilder);
        build.aggregation(termsAggregationBuilder);// 添加聚合
        request.source(build);// 设置查询请求
        // 执行查询
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(termAggName);// 获取聚合返回对象
            for(Terms.Bucket bucket : terms.getBuckets()){
                String termsKey = bucket.getKey().toString();
                System.out.println("termsKey="+termsKey);
                Sum sum = bucket.getAggregations().get(sumAggName);
                String key = sum.getName();
                double sumVal = sum.getValue();
                System.out.println("key="+key+", count="+sumVal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getExternalBucketAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String aggNameCity = "my_terms_city"; // 按城市聚合的名称
        // 定义terms聚合，指定字段为城市
        TermsAggregationBuilder termsAggCity = AggregationBuilders.terms(aggNameCity).field("city");
        String aggNameFullRoom = "my_terms_full_room"; // 满房状态聚合的名称
        //定义terms聚合，指定字段为满房状态
        TermsAggregationBuilder termsArrFullRoom = AggregationBuilders.terms(aggNameFullRoom).field("full_room");
        String sumAggName = "my_sum"; // sum聚合名称
        // 定义sum聚合，指定字段为价格
        SumAggregationBuilder sumAgg = AggregationBuilders.sum(sumAggName).field("price");

        //定义聚合的父子关系
        termsArrFullRoom.subAggregation(sumAgg);
        termsAggCity.subAggregation(termsArrFullRoom);
        builder.aggregation(termsAggCity); // 添加聚合
        request.source(builder);

        // 执行查询
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(aggNameCity);// 获取聚合返回的对象
            for(Terms.Bucket bucket : terms.getBuckets()){ // 遍历第一层bucket
                // 获取第一层bucket名称
                String termsKeyCity = bucket.getKey().toString();
                System.out.println("--------------- termsKeyCity = "+termsKeyCity+" ---------------------");
                Terms termsFullRoom = bucket.getAggregations().get(aggNameFullRoom);
                // 遍历第二层bucket
                for (Terms.Bucket fullRoomBucket : termsFullRoom.getBuckets()){
                    // 获取第二层bucket名称
                    String termKeyFullRoom = fullRoomBucket.getKeyAsString();
                    System.out.println("termsKeyFullRoom = "+termKeyFullRoom);
                    // 获取聚合指标
                    Sum sum = fullRoomBucket.getAggregations().get(sumAggName);
                    String key = sum.getName();// 获取聚合指标名称
                    double sumVal = sum.getValue(); // 获取聚合指标值
                    System.out.println("key = "+key+" count = "+sumVal);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getGeoDistanceAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String geoDistanceAggName = "location"; // 聚合名称
        // 定义GeoDistance聚合
        GeoDistanceAggregationBuilder geoDistanceAgg = AggregationBuilders
                .geoDistance(geoDistanceAggName, new GeoPoint(39.915143, 116.4039));
        geoDistanceAgg.unit(DistanceUnit.KILOMETERS);
        geoDistanceAgg.field("location");
        // 指定分桶范围规则
        geoDistanceAgg.addRange(new GeoDistanceAggregationBuilder.Range(null, 0d, 3d));
        geoDistanceAgg.addRange(new GeoDistanceAggregationBuilder.Range(null, 3d, 110d));
        geoDistanceAgg.addRange(new GeoDistanceAggregationBuilder.Range(null, 110d, null));
        String minAggName = "my_min"; // min聚合名称
        // 定义sum聚合，指定字段为价格
        MinAggregationBuilder minAgg = AggregationBuilders.min(minAggName).field("price");
        minAgg.missing(100);// 指定默认值
        // 定义聚合的父子关系
        geoDistanceAgg.subAggregation(minAgg);
        sourceBuilder.aggregation(geoDistanceAgg);// 添加聚合
        request.source(sourceBuilder);//设置查询请求
        // 执行查询
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 获取GeoDistance聚合返回的对象
            ParsedGeoDistance range = aggregations.get(geoDistanceAggName);
            for(Range.Bucket bucket : range.getBuckets()){
                // 获取bucket名称的字符串形式
                String termsKey = bucket.getKeyAsString();
                System.out.println("termsKey="+termsKey);
                ParsedMin min = bucket.getAggregations().get(minAggName);
                String key = min.getName(); // 获取聚合名称
                double minVal = min.getValue();
                System.out.println("key="+key+" min="+minVal); //打印结果
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
