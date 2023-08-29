package song.spring4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
public class LogAspect {

    @Aspect
    @Order(0)
    @Component
    @RequiredArgsConstructor
    public static class log {

        @Around("song.spring4.aop.Pointcuts.servicePointcut()")
        public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Logging] {} ({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            Object result;

            Long resultTime;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } finally {
                stopWatch.stop();

                resultTime = stopWatch.getLastTaskTimeMillis();
            }

            log.info("[Logging] result time = {}",resultTime);
            return result;
        }
    }
}
