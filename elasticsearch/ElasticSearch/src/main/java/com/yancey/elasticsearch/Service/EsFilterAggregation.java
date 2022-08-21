package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsFilterAggregation {
    @Autowired
    RestHighLevelClient client;

    public void getFilterAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String filterAggName = "my_term";
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("full_room", false);
        FilterAggregationBuilder filterAggregationBuilder = AggregationBuilders.filter(filterAggName, termQueryBuilder);
        String avgAggName = "my_avg";// avg聚合的名称
        // 定义聚合，指定字段为价格
        AvgAggregationBuilder avgAgg = AggregationBuilders.avg(avgAggName).field("price");
        // 为Filter聚合添加子聚合
        filterAggregationBuilder.subAggregation(avgAgg);
        builder.aggregation(filterAggregationBuilder);// 添加聚合
        // 构建term查询
        builder.query(QueryBuilders.termQuery("city", "天津"));
        request.source(builder); // 设置查询请求
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            //获取sum聚合返回的对象
            ParsedFilter filter = aggregations.get(filterAggName);
            Avg avg = filter.getAggregations().get(avgAggName);
            String key = avg.getName(); // 获取聚合名称
            double avgVal = avg.getValue();
            System.out.println("key="+key+", avgVal="+avgVal);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
