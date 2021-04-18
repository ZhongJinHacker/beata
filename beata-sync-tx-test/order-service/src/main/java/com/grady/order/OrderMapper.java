package com.grady.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    @Insert("insert into `order`(name) values(#{name})")
    void insert(@Param("name") String name);

}
