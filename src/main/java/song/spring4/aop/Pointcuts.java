package song.spring4.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* song.spring4.service.*.*(..))")
    public void servicePointcut() {
    }
}
