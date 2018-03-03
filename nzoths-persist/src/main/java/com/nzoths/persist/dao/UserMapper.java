package com.nzoths.persist.dao;

import com.nzoths.pojo.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}