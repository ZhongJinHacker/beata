package com.beata.sync.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.TransactionStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSyncTxModel {

    private String xid;

    private TransactionStatus transactionStatus;
}
