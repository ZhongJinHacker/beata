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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeataServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Map<String, List<BranchChannel>> xidBranchMap = new ConcurrentHashMap<>();

    private static final Map<String, String> xidStatusMap = new ConcurrentHashMap<>();

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

        if (cmd.equals(CmdConstants.RequestCmd.CREATE_XID)) {
            handleCreateXid(ctx, request);
        } else if (cmd.equals(CmdConstants.RequestCmd.COMMIT_XID)) {
            handleCommitXid(ctx, request);
        } else if (cmd.equals(CmdConstants.RequestCmd.ROLLBACK_XID)) {
            handleRollbackXid(ctx, request);
        } else if (cmd.equals(CmdConstants.RequestCmd.COMMIT_BRANCH)) {
            handleBranchCommit(ctx, request);
        } else if (cmd.equals(CmdConstants.RequestCmd.ROLLBACK_BRANCH)) {
            handleBranchRollback(ctx, request);
        } else {
            throw new RuntimeException("illegal cmd!");
        }
    }

    private void handleBranchRollback(ChannelHandlerContext ctx, RpcRequest request) {
        xidStatusMap.put(request.getXid(), CmdConstants.RequestCmd.ROLLBACK_XID);
        RpcResponse response = new RpcResponse();
        response.setXid(request.getXid());
        response.setSuccess(false);
        response.setFromId(request.getId());
        response.setResponseCmd(CmdConstants.ResponseCmd.BRANCH_ROLLBACK);
        ctx.channel().writeAndFlush(JSON.toJSONString(response));
    }

    private void handleBranchCommit(ChannelHandlerContext ctx, RpcRequest request) {
        String xid = request.getXid();
        String status = xidStatusMap.get(xid);
        if (CmdConstants.RequestCmd.COMMIT_XID.equals(status)) {
            RpcResponse response = new RpcResponse();
            response.setXid(request.getXid());
            response.setSuccess(true);
            response.setFromId(request.getId());
            response.setResponseCmd(CmdConstants.ResponseCmd.BRANCH_COMMIT);
            ctx.channel().writeAndFlush(JSON.toJSONString(response));
        } else if (CmdConstants.RequestCmd.ROLLBACK_XID.equals(status)) {
            RpcResponse response = new RpcResponse();
            response.setXid(request.getXid());
            response.setSuccess(false);
            response.setFromId(request.getId());
            response.setResponseCmd(CmdConstants.ResponseCmd.BRANCH_ROLLBACK);
            ctx.channel().writeAndFlush(JSON.toJSONString(response));
        } else {
            List<BranchChannel> branchChannels = xidBranchMap.get(request.getXid());
            synchronized (xidBranchMap) {
                BranchChannel branchChannel = new BranchChannel();
                branchChannel.setChannel(ctx.channel());
                branchChannel.getRequestIds().add(request.getId());
                branchChannels.add(branchChannel);
            }
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

        List<BranchChannel> branchChannels = new ArrayList<>();
        xidBranchMap.put(xid, branchChannels);
        xidStatusMap.put(xid, CmdConstants.RequestCmd.CREATE_XID);
    }

    private void handleCommitXid(ChannelHandlerContext ctx, RpcRequest request) {
        xidStatusMap.put(request.getXid(), CmdConstants.RequestCmd.COMMIT_XID);
        List<BranchChannel> branchChannels = Optional.ofNullable(xidBranchMap.get(request.getXid()))
                .orElse(new ArrayList<>());
        for (BranchChannel channel : branchChannels) {
            for (Integer requestId : channel.requestIds) {
                RpcResponse response = new RpcResponse();
                response.setXid(request.getXid());
                response.setSuccess(true);
                response.setFromId(requestId);
                response.setResponseCmd(CmdConstants.ResponseCmd.BRANCH_COMMIT);
                channel.getChannel().writeAndFlush(JSON.toJSONString(response));
            }
        }
        xidBranchMap.put(request.getXid(), new ArrayList<>());
    }

    private void handleRollbackXid(ChannelHandlerContext ctx, RpcRequest request) {
        xidStatusMap.put(request.getXid(), CmdConstants.RequestCmd.ROLLBACK_XID);
        List<BranchChannel> branchChannels = xidBranchMap.get(request.getXid());
        for (BranchChannel channel : branchChannels) {
            for (Integer requestId : channel.requestIds) {
                RpcResponse response = new RpcResponse();
                response.setXid(request.getXid());
                response.setSuccess(true);
                response.setFromId(requestId);
                response.setResponseCmd(CmdConstants.ResponseCmd.BRANCH_ROLLBACK);
                channel.getChannel().writeAndFlush(JSON.toJSONString(response));
            }
        }
        xidBranchMap.remove(request.getXid());
    }
}
