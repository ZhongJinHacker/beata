package com.beata.sync.tm.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TmClientTest {


    @Before
    public void setUp() throws Exception {
        TmClient.getInstance().init("localhost", 8888);
    }

    @Test
    public void beginGlobalTransaction() {
        String xid = TmClient.getInstance().beginGlobalTransaction();
        Assert.assertTrue(xid != null);
    }

    @Test
    public void commitGlobalTransaction() {
    }

    @Test
    public void rollbackGlobalTransaction() {
    }
}