package com.beata.sync.tm.api;

import com.beata.sync.core.context.RootContext;
import com.beata.sync.enums.BeataGlobalStatus;
import com.beata.sync.enums.BeataRoleEnum;

public class GlobalSyncTransactionContext {

    private GlobalSyncTransactionContext() {
    }

    public static SyncTypeGlobalTransaction createNew() {
        return new SyncTypeGlobalTransaction(null, BeataGlobalStatus.UnKnown, BeataRoleEnum.LAUNCHER);
    }

    public static SyncTypeGlobalTransaction getCurrent() {
        String xid = RootContext.getXID();
        if (xid == null) {
            return null;
        }
        return new SyncTypeGlobalTransaction(xid, BeataGlobalStatus.Begin, BeataRoleEnum.PARTICIPANT);
    }
}
