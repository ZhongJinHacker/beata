package com.beata.sync.rm.cilent;

import com.alibaba.fastjson.JSON;
import com.beata.sync.model.MessageFuture;
import com.beata.sync.model.RpcRequest;
import com.beata.sync.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class RmClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    protected final ConcurrentHashMap<Integer, MessageFuture> futures = new ConcurrentHashMap<>();

    AtomicInteger idGenerator = new AtomicInteger(0);

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

    public boolean branchTransactionCommit(String xid) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd("commitBranch");
        rpcRequest.setId(idGenerator.getAndIncrement());
        rpcRequest.setXid(xid);


        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setResultMessage(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));

        try {
            RpcResponse ret = (RpcResponse)messageFuture.get();
            return ret.getSuccess();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }

    public boolean branchTransactionRollback(String xid) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd("rollbackBranch");
        rpcRequest.setId(idGenerator.getAndIncrement());
        rpcRequest.setXid(xid);


        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setResultMessage(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));

        try {
            RpcResponse ret = (RpcResponse)messageFuture.get();
            return ret.getSuccess();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }
}
