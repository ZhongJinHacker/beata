package com.beata.sync.server;

import com.alibaba.fastjson.JSON;
import com.beata.common.constants.CmdConstants;
import com.beata.common.model.RpcRequest;
import com.beata.common.model.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BeataServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Map<String, BranchChannel> xidBranchMap = new ConcurrentHashMap<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        channelGroup.remove(ctx.channel());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = JSON.parseObject((String) msg, RpcRequest.class);
        String cmd = request.getCmd();
        if (StringUtils.isBlank(cmd)) {
            return;
        }

        if (cmd.equals(CmdConstants.CREATE_XID)) {
            handleCreateXid(ctx, request);
        } else if (cmd.equals(CmdConstants.COMMIT_XID)) {

        } else if (cmd.equals(CmdConstants.ROLLBACK_XID)) {

        } else if (cmd.equals(CmdConstants.COMMIT_BRANCH)) {

        } else if (cmd.equals(CmdConstants.ROLLBACK_BRANCH)) {

        } else {
            throw new RuntimeException("illegal cmd!");
        }
    }

    private void handleCreateXid(ChannelHandlerContext ctx, RpcRequest request) {
        String xid = UUID.randomUUID().toString();
        System.out.println("handle create xid : " + xid);
        RpcResponse response = new RpcResponse();
        response.setXid(xid);
        response.setSuccess(true);
        response.setFromId(request.getId());
        ctx.channel().writeAndFlush(JSON.toJSONString(response));
    }
}
