package com.beata.sync.server;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BranchChannel {

    Channel channel;

    Set<Integer> requestIds = new HashSet<>();
}
