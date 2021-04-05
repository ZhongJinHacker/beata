package com.beata.sync.tm.client;

import com.alibaba.fastjson.JSON;
import com.beata.sync.tm.model.MessageFuture;
import com.beata.sync.tm.model.RpcRequest;
import com.beata.sync.tm.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class TmClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    protected final ConcurrentHashMap<Integer, MessageFuture> futures = new ConcurrentHashMap<>();

    AtomicInteger idGenerator = new AtomicInteger(0);

    public TmClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcResponse)) {
            return;
        }
        RpcResponse response = (RpcResponse)msg;
        MessageFuture messageFuture = futures.remove(response.getFromId());
        messageFuture.setResultMessage(response);
    }

    public Object createGlobalTransaction() {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd("createXid");
        rpcRequest.setId(idGenerator.getAndIncrement());

        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setResultMessage(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));
        try {
            Object ret = messageFuture.get();
            return ret;
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }

    public void commitGlobalTransaction(String xid) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd("commitXid");
        rpcRequest.setId(idGenerator.getAndIncrement());
        rpcRequest.setXid(xid);

        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setResultMessage(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));
        try {
            Object ret = messageFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }

    public void rollbackGlobalTransaction(String xid) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd("rollbackXid");
        rpcRequest.setId(idGenerator.getAndIncrement());
        rpcRequest.setXid(xid);

        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setResultMessage(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));
        try {
            Object ret = messageFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }
}

