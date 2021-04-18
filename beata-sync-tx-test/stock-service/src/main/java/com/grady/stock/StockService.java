package com.grady.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    public void doStock() {
        stockMapper.insert("hello world111");
        int i = 1/0;
        System.out.println();
    }
}
