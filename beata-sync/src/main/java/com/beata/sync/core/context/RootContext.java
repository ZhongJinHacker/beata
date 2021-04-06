package com.beata.sync.core.context;

import java.util.HashMap;
import java.util.Map;

public class RootContext {

    private final static ThreadLocal<Map<String, Object>> CONTEXT_HOLDER = ThreadLocal.withInitial(HashMap::new);


    private RootContext() {

    }

    /**
     * The constant KEY_XID.
     */
    public static final String KEY_XID = "TX_XID";

    public static String getXID() {
        return (String) CONTEXT_HOLDER.get().get(KEY_XID);
    }

    public static void setXID(String xid) {
        CONTEXT_HOLDER.get().put(KEY_XID, xid);
    }

    public static void clearXid() {
        CONTEXT_HOLDER.get().remove(KEY_XID);
    }
}
