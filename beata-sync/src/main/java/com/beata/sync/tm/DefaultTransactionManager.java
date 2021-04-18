package com.beata.sync.tm;

import com.beata.sync.tm.client.TmClient;

public class DefaultTransactionManager {

    public static String begin() {
        return TmClient.getInstance().beginGlobalTransaction();
    }

    public static void commit(String xid) {
        TmClient.getInstance().commitGlobalTransaction(xid);
    }

    public static void rollback(String xid) {
        TmClient.getInstance().rollbackGlobalTransaction(xid);
    }
}
