package com.beata.common.constants;

public class CmdConstants {

    public static class RequestCmd {
        public static final String CREATE_XID = "createXid";

        public static final String COMMIT_XID = "commitXid";

        public static final String ROLLBACK_XID = "rollbackXid";

        public static final String BEGIN_BRANCH = "beginBranch";

        public static final String COMMIT_BRANCH = "commitBranch";

        public static final String ROLLBACK_BRANCH = "rollbackBranch";
    }

    public static class ResponseCmd {
        public static final String BRANCH_COMMIT = "ACTION_BRANCH_COMMIT";

        public static final String BRANCH_ROLLBACK = "ACTION_BRANCH_COMMIT";

        public static final String XID_COMMIT = "ACTION_XID_COMMIT";

        public static final String XID_ROLLBACK = "ACTION_XID_ROLLBACK";

    }
}
