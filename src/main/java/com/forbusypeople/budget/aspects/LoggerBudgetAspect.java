package com.forbusypeople.budget.aspects;

import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.users.UserLogInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "com.forbusypeople =>")
@RequiredArgsConstructor
public class LoggerBudgetAspect {

    private final UserLogInfoService userLogInfoService;

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

    @Around("@annotation(com.forbusypeople.budget.aspects.annotations.SetLoggedUser)")
    public Object setLoggedUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (!(arg instanceof UserEntity)) {
                continue;
            }

            args[i] = userLogInfoService.getLoggedUserEntity();
            return joinPoint.proceed(args);
        }

        return joinPoint.proceed();
    }

}