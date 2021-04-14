package com.grady.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    @Insert("insert into order_test(name) values(#{name})")
    void insert(@Param("name") String name);

}
