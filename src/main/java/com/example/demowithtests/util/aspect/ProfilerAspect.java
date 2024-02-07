package com.example.demowithtests.util.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static com.example.demowithtests.util.aspect.ColorConstant.*;


@Aspect
@Component
public class ProfilerAspect {
    @Around("execution(* *(..)) && @within(com.example.demowithtests.util.annotation.Profiler)")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch(joinPoint.toShortString());
        stopWatch.start(stopWatch.toString());
        Object result = joinPoint.proceed();
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println(ANSI_YELLOW + stopWatch.prettyPrint() + "totalTime:" + " " + totalTimeMillis + " " + "ms" + ANSI_RESET);
        return result;
    }
}
