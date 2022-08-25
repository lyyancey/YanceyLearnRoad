package com.yancey.hotelsearchweb.service;

import com.yancey.hotelsearchweb.dto.HotelSearchResult;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Service
public class EsQueryService {
    public SearchSourceBuilder getSearchQuery(HotelSearchResult hotelSearchResult) {
        return null;
    }
}
