package com.beata.sync.model;

import lombok.Data;

@Data
public class RpcResponse {

    private Integer fromId;

    private String xid;

    private Boolean success;
}
