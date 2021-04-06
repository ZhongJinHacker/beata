package com.beata.sync.rm;

import com.beata.sync.rm.cilent.RmClient;

public class DefaultResourceManager {

    public static void branchTransactionBegin() {

    }

    public static boolean branchTransactionCommit(String xid) {
        return RmClient.getInstance().branchTransactionCommit(xid);
    }

    public static boolean branchTransactionRollback(String xid) {
        return RmClient.getInstance().branchTransactionRollback(xid);
    }
}
