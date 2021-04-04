package com.grady.sync.service.impl;


import com.grady.sync.mapper.UserMapper;
import com.grady.sync.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxServiceImpl implements TxService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public String insertUseLocalTx(String name, String passwd) {
        userMapper.insert(name, passwd);
        //int fail = 1/0;
        return "success";
    }
}
