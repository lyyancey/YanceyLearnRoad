package com.yancey.hotelsearchweb.service;

import com.yancey.hotelsearchweb.dto.Hotel;
import com.yancey.hotelsearchweb.dto.HotelAgg;
import com.yancey.hotelsearchweb.dto.HotelSearchResult;
import com.yancey.hotelsearchweb.dto.request.HotelSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HotelSearchService {
    @Autowired
    EsQueryService esQueryService;
    @Autowired
    RestHighLevelClient client;
    public HotelSearchResult getCommonSearch(HotelSearchRequest hotelSearchRequest) {
        // 创建搜索结果对象
        HotelSearchResult hotelSearchResult = new HotelSearchResult();
        // 设置当前页码
        hotelSearchResult.setPageNo(hotelSearchRequest.getPageNo());
        // 根据搜索条件获取总页数
        long searchCount = getTotalCount(hotelSearchResult);
        hotelSearchResult.setTotalCount(searchCount);
        // 开始搜索
        SearchSourceBuilder builder = esQueryService.getSearchQuery(hotelSearchResult);
        // 定义需要返回的字段数组
        String[] fields = new String[]{"title", "price", "favourable_percent", "impression",
                "business_district", "address", "pic", "location", "star"};
        // 设定需要返回的字段数组
        builder.fetchSource(fields, null);
        // 新建搜索请求
        SearchRequest request = new SearchRequest("hotel");
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            List<Hotel> hotelList = getListFromEsResult(response, hotelSearchRequest.getLat(), hotelSearchRequest.getLon());
            hotelSearchResult.setHotelList(hotelList);
            // 获取聚合结果
            Aggregations aggregations = response.getAggregations();
            // 对聚合结果进行处理和封装
            Map<String, HotelAgg> aggMap = getAggMapFromEsAgg(aggregations);
            hotelSearchResult.setAggMap(aggMap);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return hotelSearchResult;
    }

    private Map<String, HotelAgg> getAggMapFromEsAgg(Aggregations aggregations) {
        // @Todo
        return null;
    }

    private List<Hotel> getListFromEsResult(SearchResponse response, Float lat, Float lon) {
        // @Todo
        return null;
    }

    private long getTotalCount(HotelSearchResult hotelSearchResult) {
        // @Todo
        return 0l;
    }
}
