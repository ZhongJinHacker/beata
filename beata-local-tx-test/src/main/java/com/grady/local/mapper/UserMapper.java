package com.grady.local.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    @Insert("insert into t_user(user_name, password) values (#{name}, #{password})")
    void insert(@Param("name") String name, @Param("password") String password);
}
