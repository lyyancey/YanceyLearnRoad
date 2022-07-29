package com.yancey.elasticsearch.Service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 如果不想让检索词频率TF（Term Frequency）对搜索结果排序有影响，只想过滤某个文本字段是否包含某个词，可以使用Constant Score将查询语句包装起来。
 */
@Service
public class EsConstantScore {
    @Autowired
    RestHighLevelClient client;
    private List<String> getSearchResult(SearchHits searchHits){
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
    public List<String> constantScoreSearch() {
        // 新建搜索请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建设施包含停车场的constant score 查询
        ConstantScoreQueryBuilder constantScoreQueryBuilder  =
                new ConstantScoreQueryBuilder(QueryBuilders.matchQuery("amenities", "停车场"));
        searchSourceBuilder.query(constantScoreQueryBuilder);
        constantScoreQueryBuilder.boost(2.0f);
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> functionScoreSearch(){
        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 搜检term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", "北京");
        // 构建随机函数
        ScoreFunctionBuilder<?> scoreFunctionBuilder = ScoreFunctionBuilders.randomFunction();
        // 构建Function Score 查询
        FunctionScoreQueryBuilder funcQuery = QueryBuilders.
            functionScoreQuery(termQueryBuilder,scoreFunctionBuilder).
            boostMode(CombineFunction.SUM);
        searchSourceBuilder.query(funcQuery);
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public List<String> matchSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建match查询，并设置operator值为and
        searchSourceBuilder.query(QueryBuilders.matchQuery("city", "北京").operator(Operator.AND));
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> mutiMatchSearch(){
        //新建搜索请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建muti_match查询，从"title"和"amenities"字段查询"假日"
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("假日", "title", "amenities"));
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> matchPhraseSearch(){
        // 新建搜索请求
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建match_phrase查询,并设置slop值为2
        QueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("title", "文雅酒店").slop(2);
        searchSourceBuilder.query(matchPhraseQueryBuilder);
        //设置查询
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> geoDistanceSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建geo_distance查询，设置基准点坐标和周边距离
        searchSourceBuilder.query(QueryBuilders.geoDistanceQuery("location").
                distance(5, DistanceUnit.KILOMETERS).point(39.915143, 116.4039));
        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> geoPolygonSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel002");
        // 新建geo_distance查询，设置基准点坐标和周边距离
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 新建多边形顶点列表
        List<GeoPoint> geoPointList = new ArrayList<>();
        geoPointList.add(new GeoPoint(39.959829, 116.417088));
        geoPointList.add(new GeoPoint(39.960272, 116.432035));
        geoPointList.add(new GeoPoint(39.965802, 116.421399));
        searchSourceBuilder.query(QueryBuilders.geoPolygonQuery("location", geoPointList));

        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public void suggestSearch(){
        SearchRequest searchRequest = new SearchRequest("hotel_sug");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 创建completion 类型的搜索建议
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.
                completionSuggestion("query_word").prefix("如家");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        // 添加搜索建议， "hotel_zh_sug"为自定义名称
        suggestBuilder .addSuggestion("hotel_zh_sug", completionSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder); // 设置搜索建议请求
        searchRequest.source(searchSourceBuilder);

        try {
            // 进行搜索， 获取搜索结果
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            // 获取搜索建议结果
            CompletionSuggestion suggestion = response.getSuggest().getSuggestion("hotel_zh_sug");
            System.out.println("sug result: ");
            // 遍历搜索建议结果并打印
            for (CompletionSuggestion.Entry.Option option : suggestion.getOptions()){
                System.out.println("sug: "+ option.getText().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> commonSort(){
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建match查询
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "金都"));
        //设置按照价格进行降序排列
        searchSourceBuilder.sort("price", SortOrder.DESC);
        //设置按照口碑值进行降序排列
        searchSourceBuilder.sort("praise", SortOrder.DESC);

        searchRequest.source(searchSourceBuilder);
        List<String> list = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            list = getSearchResult(searchHits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public void geoDistanceSearchSort() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel002");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 创建geo_distance查询，搜索距离中心点5km范围内的酒店
        searchSourceBuilder.query(QueryBuilders.geoDistanceQuery("location")
                .distance(5, DistanceUnit.KILOMETERS).point(39.915143, 116.4039));
        // 创建geo_distance_sort排序，设定按照与中心点的距离进行升序排序
        GeoDistanceSortBuilder geoDistanceSortBuilder = SortBuilders.
                geoDistanceSort("location", 39.915143, 116.4039)
                .point(39.915143, 116.4039).unit(DistanceUnit.KILOMETERS)
                .order(SortOrder.ASC);
        //设置排序规则
        searchSourceBuilder.sort(geoDistanceSortBuilder);
        // 设置查询
        searchRequest.source(searchSourceBuilder);
        // 开始搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        // 获取搜索结果
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("search result distance sort :");
        // 开始遍历搜索结果
        for(SearchHit searchHit : searchHits){
            // 到酒店距离中心点的距离
            double geoDistance = (double) searchHit.getSortValues()[0];
            // 以map的形式获取文档_source内容
            Map<String, Object> sourceMap = searchHit.getSourceAsMap();
            Object title = sourceMap.get("title");
            Object city = sourceMap.get("city");
            // 打印结果
            System.out.println("title = "+title+", city = "+city+", geoDistance = "+geoDistance);
        }


    }
}
