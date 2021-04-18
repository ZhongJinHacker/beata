package com.grady.stock;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {

    @Insert("insert into stock(name) values(#{name})")
    void insert(@Param("name") String name);
}
