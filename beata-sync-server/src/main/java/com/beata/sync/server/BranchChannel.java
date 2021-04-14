package com.beata.sync.server;

import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

@Data
public class BranchChannel {

    NioSocketChannel channel;

    String branchStatus;
}
