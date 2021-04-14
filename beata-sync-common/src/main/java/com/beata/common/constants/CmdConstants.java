package com.beata.common.constants;

public class CmdConstants {

    public static class RequestCmd {
        public static final String CREATE_XID = "createXid";

        public static final String COMMIT_XID = "commitXid";

        public static final String ROLLBACK_XID = "rollbackXid";

        public static final String COMMIT_BRANCH = "commitBranch";

        public static final String ROLLBACK_BRANCH = "rollbackBranch";
    }

    public static class ResponseCmd {
        public static final String BRANCH_COMMIT = "ACTiON_BRANCH_COMMIT";

        public static final String BRANCH_ROLLBACK = "ACTiON_BRANCH_COMMIT";

    }
}
