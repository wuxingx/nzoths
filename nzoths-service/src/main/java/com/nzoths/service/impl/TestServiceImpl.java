package com.nzoths.service.impl;

import com.nzoths.persist.dao.UserMapper;
import com.nzoths.pojo.User;
import com.nzoths.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ITestService")
public class TestServiceImpl implements ITestService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(String name, String passowrd) {
        User user = new User();
        user.setName(name);
        user.setPassword(passowrd);
        userMapper.insertSelective(user);
    }
}
