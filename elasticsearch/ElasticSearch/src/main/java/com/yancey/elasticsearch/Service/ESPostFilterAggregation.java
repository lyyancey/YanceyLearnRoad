package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESPostFilterAggregation {
    @Autowired
    RestHighLevelClient client;
    public void getPostFilterAggSearch(){
        SearchRequest request = new SearchRequest("hotel003");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String avgAggName = "my_agg"; // avg聚合名称
        // 定义sum聚合，指定字段为价格
        AvgAggregationBuilder avgAgg = AggregationBuilders.avg(avgAggName).field("price");
        avgAgg.missing(200); // 设置默认值为200
        builder.aggregation(avgAgg); // 添加聚合
        // 构建term查询
        builder.query(QueryBuilders.matchQuery("title", "假日"));
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", "北京");
        builder.postFilter(termQueryBuilder);
        request.source(builder); // 设置查询请求

        try {
            //执行搜索
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            Avg avg = aggregations.get(avgAggName);
            String key = avg.getName();
            double avgVal = avg.getValue();
            System.out.println("key = "+key+", avgVal = "+avgVal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
