package com.beata.sync.tm.client;

import com.alibaba.fastjson.JSON;
import com.beata.common.constants.CmdConstants;
import com.beata.common.model.RpcRequest;
import com.beata.common.model.RpcResponse;
import com.beata.sync.model.MessageFuture;
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
        RpcResponse response =  JSON.parseObject((String)msg, RpcResponse.class);
        MessageFuture messageFuture = futures.remove(response.getFromId());
        messageFuture.setResultMessage(response);
    }

    public RpcResponse createGlobalTransaction() {
        return sendTmCmd(CmdConstants.RequestCmd.CREATE_XID, null);
    }

    public void commitGlobalTransaction(String xid) {
        sendTmCmd(CmdConstants.RequestCmd.COMMIT_XID, xid);
    }

    public void rollbackGlobalTransaction(String xid) {
        sendTmCmd(CmdConstants.RequestCmd.COMMIT_XID, xid);
    }

    private RpcResponse sendTmCmd(String cmd, String xid) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setCmd(cmd);
        rpcRequest.setId(idGenerator.getAndIncrement());
        rpcRequest.setXid(xid);

        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setRpcRequest(rpcRequest);
        futures.put(rpcRequest.getId(), messageFuture);
        context.writeAndFlush(JSON.toJSONString(rpcRequest));
        try {
            return  messageFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("get xid error: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("InterruptedException, get xid error: " + e);
        }
    }
}

