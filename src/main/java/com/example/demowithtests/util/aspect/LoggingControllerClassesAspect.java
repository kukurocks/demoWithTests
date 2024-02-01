package com.example.demowithtests.util.aspect;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static com.example.demowithtests.util.aspect.ColorConstant.*;

@Log4j2
@Aspect
@Component
public class LoggingControllerClassesAspect {

    @Pointcut("execution(public * com.example.demowithtests.web..*.*(..))")
    public void callAtMyControllersPublicMethods() {
    }

    @Before("callAtMyControllersPublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info(ANSI_GREEN + "Controller: " + methodName + " - start." + ANSI_RESET);
    }

    @AfterReturning(value = "callAtMyControllersPublicMethods()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info(ANSI_CYAN + "Controller: " + methodName + " - end." + ANSI_RESET);
    }
}
