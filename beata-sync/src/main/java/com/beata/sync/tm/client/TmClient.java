package com.beata.sync.tm.client;

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

public class TmClient {

    private TmClientHandler handler;

    private TmClient() {
    }

    private static TmClient instance;

    public static TmClient getInstance() {
        if (instance == null) {
            synchronized (TmClient.class) {
                if (instance == null) {
                    instance = new TmClient();
                }
            }
        }
        return instance;
    }


    public void init(String host, int port) {
        handler = new TmClientHandler();
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

    public String beginGlobalTransaction() {
        return (String) handler.createGlobalTransaction();
    }

    public void commitGlobalTransaction(String xid) {
        handler.commitGlobalTransaction(xid);
    }

    public void rollbackGlobalTransaction(String xid) {
        handler.rollbackGlobalTransaction(xid);
    }
}
