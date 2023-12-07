package song.spring4.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* song.spring4.domain.*.controller.*.*(..))")
    public void controllerPointcut() {}

    @Pointcut("execution(* song.spring4.domain.*.service.*.*(..))")
    public void servicePointcut() {}

    @Pointcut("execution(* song.spring4.domain.*.repository.*.*(..))")
    public void repositoryPointcut() {}
}
