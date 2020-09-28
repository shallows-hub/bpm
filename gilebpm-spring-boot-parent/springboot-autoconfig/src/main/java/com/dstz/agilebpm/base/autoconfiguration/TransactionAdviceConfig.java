package com.dstz.agilebpm.base.autoconfiguration;

import com.dstz.base.db.transaction.AbDataSourceTransactionManager;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@Aspect
public class TransactionAdviceConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.dstz.*.*.manager.*.*(..))||execution(* com.dstz.*.manager.*.*(..))";

    @Bean(name = {"abTransactionManager"})
    public PlatformTransactionManager platformTransactionManager() {
        return new AbDataSourceTransactionManager();
    }

    @Bean(name = {"abTransactionInterceptor"})
    public TransactionInterceptor txAdvice(PlatformTransactionManager abTransactionManager) {
        DefaultTransactionAttribute requiredDTA = new DefaultTransactionAttribute();
        requiredDTA.setPropagationBehavior(0);
        DefaultTransactionAttribute requiredReadonlyDTA = new DefaultTransactionAttribute();
        requiredReadonlyDTA.setPropagationBehavior(0);
        requiredReadonlyDTA.setReadOnly(true);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("*", requiredDTA);
        source.addTransactionalMethod("get*", requiredReadonlyDTA);
        source.addTransactionalMethod("query*", requiredReadonlyDTA);
        source.addTransactionalMethod("find*", requiredReadonlyDTA);
        source.addTransactionalMethod("is*", requiredReadonlyDTA);
        return new TransactionInterceptor(abTransactionManager, source);
    }

    @Bean(name = {"abAdvisor"})
    public Advisor txAdviceAdvisor(PlatformTransactionManager abTransactionManager) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice(abTransactionManager));
    }
}