package com.beata.sync.model;

import lombok.Data;

@Data
public class RpcRequest {

    private Integer id;

    private String cmd;

    private String xid;
}
