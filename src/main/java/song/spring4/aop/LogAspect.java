package song.spring4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import song.spring4.service.LogEntityService;

import java.time.LocalDateTime;

@Slf4j
public class LogAspect {

    @Aspect
    @Order(0)
    @Component
    @RequiredArgsConstructor
    public static class log {

        private final LogEntityService logEntityService;

        @Around("song.spring4.aop.Pointcuts.servicePointcut() && !execution(* song.spring4.service.LogEntityService.*(..))")
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
                logEntityService.save(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                        LocalDateTime.now(), resultTime);
            }

            log.info("[Logging] result time = {}",resultTime);
            return result;
        }
    }
}
