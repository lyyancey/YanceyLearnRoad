package com.yancey.elasticsearch.Service;

import com.yancey.elasticsearch.pojo.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EsRepository extends CrudRepository<Hotel, String> {
    List<Hotel> findByTitleLike(String title);
}
