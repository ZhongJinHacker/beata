package com.grady.local.controller;

import com.grady.local.service.LocalTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/local")
public class LocalTxController {

    @Autowired
    private LocalTxService localTxService;

    @PostMapping(value = "/tx")
    public String testLocalTx() {
        String ret = localTxService.insertUseLocalTx("jiang", "123456");
        return ret;
    }
}
