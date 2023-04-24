package com.web.common.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
@Log4j2
public class LogExecutionTimeAspect {
    @Around("@annotation(com.web.common.annotation.LogExecutionTime)") // LogExecutionTime 애노테이션 주변에 코드를 적용함
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed(); // joinPoint 는 LogExecutionTime 애노테이션이 붙어있는 메서드이다. '.proceed()'로 메서드를 실행한다.

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        return proceed; // LogExecutionTime 애너테이션이 붙어있는 메서드를 실행한 결과를 리턴한다.
    }
}
