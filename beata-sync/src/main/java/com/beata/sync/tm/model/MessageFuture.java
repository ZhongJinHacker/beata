package com.beata.sync.tm.model;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MessageFuture {

    private RpcRequest rpcRequest;

    private transient CompletableFuture<Object> origin = new CompletableFuture<>();

    public Object get() throws ExecutionException, InterruptedException {
        return origin.get();
    }

    public void setResultMessage(Object obj) {
        origin.complete(obj);
    }
}
