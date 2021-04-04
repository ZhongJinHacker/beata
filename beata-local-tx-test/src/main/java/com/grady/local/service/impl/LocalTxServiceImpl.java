package com.grady.local.service.impl;

import com.grady.local.mapper.UserMapper;
import com.grady.local.service.LocalTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalTxServiceImpl implements LocalTxService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public String insertUseLocalTx(String name, String passwd) {
        userMapper.insert(name, passwd);
        return "success";
    }
}
