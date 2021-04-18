package com.grady.order;

import com.beata.sync.annotations.GlobalSyncTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @GlobalSyncTransaction
    public void doOrder() {
        orderMapper.insert("jiang123");
        int i = 1/0;
        System.out.println(i);
    }
}
