package com.forbusypeople.budget.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "com.forbusypeople =>")
public class LoggerBudgetAspect {

    @Around("@annotation(com.forbusypeople.budget.aspects.annotations.LoggerInfo)")
    public Object loggerInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering into method: {}", joinPoint.getSignature().getName());

        return joinPoint.proceed();
    }

    @Around("@annotation(com.forbusypeople.budget.aspects.annotations.LoggerDebug)")
    public Object loggerDebug(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.debug("Method arg: {}", arg.toString());
        }

        return joinPoint.proceed();
    }

}