package com.beata.sync.rm.cilent;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class RmClient {

    private static RmClient instance;

    private RmClientHandler handler;

    private RmClient() {
    }

    public static RmClient getInstance() {
        if (instance == null) {
            synchronized (RmClient.class) {
                if (instance == null) {
                    instance = new RmClient();
                }
            }
        }
        return instance;
    }

    public void init(String host, int port) {
        handler = new RmClientHandler();
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast("handler", handler);
                    }
                });

        try {
            b.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean branchTransactionCommit(String xid) {
        return handler.branchTransactionCommit(xid);
    }

    public boolean branchTransactionRollback(String xid) {
        return handler.branchTransactionRollback(xid);
    }


    public void branchTransactionBegin(String xid) {
        handler.branchTransactionBegin(xid);

    }
}
