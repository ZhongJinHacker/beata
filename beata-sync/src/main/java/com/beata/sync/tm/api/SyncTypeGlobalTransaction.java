package com.beata.sync.tm.api;

import com.beata.sync.core.context.RootContext;
import com.beata.sync.enums.BeataGlobalStatus;
import com.beata.sync.enums.BeataRoleEnum;
import com.beata.sync.tm.DefaultTransactionManager;

public class SyncTypeGlobalTransaction {

    private String xid;

    private BeataGlobalStatus globalStatus;

    private BeataRoleEnum role;

    public SyncTypeGlobalTransaction(String xid, BeataGlobalStatus status, BeataRoleEnum role) {
        this.xid = xid;
        this.globalStatus = status;
        this.role = role;
    }

    public void beginTransaction() {
        String xid = DefaultTransactionManager.begin();
        this.globalStatus = BeataGlobalStatus.Begin;
        RootContext.setXID(xid);
    }

    public void commitTransaction() {
        String xid = RootContext.getXID();
        DefaultTransactionManager.commit(xid);
        this.globalStatus = BeataGlobalStatus.Committed;
    }

    public void rollbackTransaction() {
        String xid = RootContext.getXID();
        DefaultTransactionManager.rollback(xid);
        this.globalStatus = BeataGlobalStatus.Rollbacked;
    }
}
