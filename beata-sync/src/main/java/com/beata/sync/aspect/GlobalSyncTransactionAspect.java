package com.beata.sync.aspect;

import com.beata.sync.annotations.GlobalSyncTransaction;
import com.beata.sync.tm.api.GlobalSyncTransactionContext;
import com.beata.sync.tm.api.SyncTypeGlobalTransaction;
import com.beata.sync.tm.client.TmClient;
import com.beata.sync.tm.properties.BeataServerProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;

@Aspect
public class GlobalSyncTransactionAspect implements InitializingBean {

    private BeataServerProperties serverProperties;

    public GlobalSyncTransactionAspect(BeataServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Around(value = "@annotation(globalSyncTransaction)")
    public Object around(ProceedingJoinPoint joinPoint, GlobalSyncTransaction globalSyncTransaction) throws Throwable {
        Object ret = null;

        SyncTypeGlobalTransaction tx = GlobalSyncTransactionContext.getCurrent();
        if (tx == null) {
            tx = GlobalSyncTransactionContext.createNew();
        }

        // 开启事务
        tx.beginTransaction();
        try {
            // 干活
            ret = joinPoint.proceed();
            // 提交事务
            tx.commitTransaction();
        } catch (Throwable throwable) {
            tx.rollbackTransaction();
            throwable.printStackTrace();
        }
        return ret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() {
        TmClient.getInstance().init(serverProperties.getHost(), Integer.parseInt(serverProperties.getPort()));
    }
}
