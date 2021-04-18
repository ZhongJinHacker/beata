package com.grady.order;

import com.beata.sync.annotations.GlobalSyncTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @GlobalSyncTransaction
    public void doOrder() {
        orderMapper.insert("jiang123");
        restTemplate.getForEntity("http://localhost:8082/stock/test", Void.class);
    }
}
