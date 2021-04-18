package com.beata.local.aspect;

import com.beata.local.annotations.LocalTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;
import java.sql.Connection;

@Aspect
public class LocalTransactionAspect {

    private DataSourceTransactionManager transactionManager;

    public LocalTransactionAspect(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around(value = "@annotation(localTransaction)")
    public Object around(ProceedingJoinPoint joinPoint, LocalTransaction localTransaction) throws Throwable {
        Object ret = null;
        // 开启事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute());
        try {
            // 干活
            ret = joinPoint.proceed();
            // 提交事务
            transactionManager.commit(transactionStatus);
        } catch (Throwable throwable) {
            transactionManager.rollback(transactionStatus);
            throwable.printStackTrace();
        }
        return ret;
    }
}
