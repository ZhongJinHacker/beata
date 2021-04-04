package com.grady.sync.controller;

import com.grady.sync.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sync")
public class TxController {

    @Autowired
    private TxService localTxService;

    @PostMapping(value = "/tx")
    public String testLocalTx() {
        String ret = localTxService.insertUseLocalTx("jiang-sync", "123456");
        return ret;
    }
}
