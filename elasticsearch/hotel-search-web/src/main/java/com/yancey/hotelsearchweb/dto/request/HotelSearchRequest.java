package com.yancey.hotelsearchweb.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Getter
@Setter
public class HotelSearchRequest {
    private Integer pageNo;
    private Float lat;
    private Float lon;
}
