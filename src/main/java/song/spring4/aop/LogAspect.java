package song.spring4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
public class LogAspect {

    @Aspect
    @Order(0)
    @Component
    @RequiredArgsConstructor
    public static class ControllerAop {

        @Around("song.spring4.aop.Pointcuts.controllerPointcut()")
        public Object resultTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Controller Aop] {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), joinPoint.getArgs());

            Object result;
            long startTime = System.currentTimeMillis();
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } finally {
                long endTime = System.currentTimeMillis();
                long resultTime = endTime - startTime;
                log.info("[Controller Aop] result time = {}ms", resultTime);
            }

            return result;
        }
    }

    @Aspect
    @Order(1)
    @Component
    @RequiredArgsConstructor
    public static class ServiceAop {

        @Around("song.spring4.aop.Pointcuts.servicePointcut()")
        public Object resultTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Service Aop] {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), joinPoint.getArgs());

            Object result;
            long startTime = System.currentTimeMillis();
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } finally {
                long endTime = System.currentTimeMillis();
                long resultTime = endTime - startTime;
                log.info("[Service Aop] result time = {}ms", resultTime);
            }

            return result;
        }
    }

    @Aspect
    @Order(2)
    @Component
    @RequiredArgsConstructor
    public static class RepositoryAop {

        @Around("song.spring4.aop.Pointcuts.repositoryPointcut()")
        public Object resultTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Repository Aop] {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), joinPoint.getArgs());

            Object result;
            long startTime = System.currentTimeMillis();
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } finally {
                long endTime = System.currentTimeMillis();
                long resultTime = endTime - startTime;
                log.info("[Repository Aop] result time = {}ms", resultTime);
            }

            return result;
        }
    }
}
