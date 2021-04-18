package com.beata.sync.model;

import com.beata.common.model.RpcRequest;
import com.beata.common.model.RpcResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MessageFuture {

    private RpcRequest rpcRequest;

    private transient CompletableFuture<RpcResponse> origin = new CompletableFuture<>();

    public RpcResponse get() throws ExecutionException, InterruptedException {
        return origin.get();
    }

    public void setRpcRequest(RpcRequest request) {
        this.rpcRequest = request;
    }

    public void setResultMessage(RpcResponse obj) {
        origin.complete(obj);
    }
}
