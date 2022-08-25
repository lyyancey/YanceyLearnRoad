package com.yancey.hotelsearchweb.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Setter
@Getter
public class HotelSearchResult {
    private Integer pageNo;
    private Integer requestId;
    private Long totalCount;
    private List<Hotel> hotelList;
    private Map<String, HotelAgg> aggMap;
}
