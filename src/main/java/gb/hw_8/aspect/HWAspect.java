package gb.hw_8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class HWAspect {

    private Instant timeStart;

    @Pointcut("within(@gb.hw_8.aspect.Timer *)")
    public void beansAnnotatedWith() {

    }

    @Pointcut("@annotation(gb.hw_8.aspect.Timer)")
    public void methodsAnnotatedWith() {

    }

    @Before("methodsAnnotatedWith() || beansAnnotatedWith()")
    public void beforeTimer(JoinPoint joinPoint) {
        timeStart = Instant.now();
    }

    @After("methodsAnnotatedWith() || beansAnnotatedWith()")
    public void afterTimer(JoinPoint joinPoint) {
        String completionTime = String.valueOf(Duration.between(Instant.now(), timeStart));
        log.info("{} - {} #{}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), completionTime);
    }

    @Pointcut("@annotation(gb.hw_8.aspect.RecoverException)")
    public void recoverExcept() {
    }

    @Around("recoverExcept()")
    public Object recoverExceptionAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object returnValue = joinPoint.proceed();
            log.info("result = {}", returnValue);
            return returnValue;
        } catch (Throwable e) {
//            List<Class<? extends RuntimeException>> exceptions = extractExceptions(joinPoint);
            if (checkExceptions(joinPoint, e)) {
                log.info("exception = [{}, {}]", e.getClass(), e.getMessage());
                throw e;
            }
            log.info("NO throwable exception - {}", e.getMessage());
            return null;
        }
    }

    public boolean checkExceptions(JoinPoint joinPoint, Throwable e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RecoverException annotation = methodSignature.getMethod().getAnnotation(RecoverException.class);
        List<Class<? extends RuntimeException>> exceptions = Arrays.stream(annotation.noRecoverFor()).toList();
        for (Class<? extends RuntimeException> exception : exceptions) {
            log.warn("{}", exception);
            if (exception.isAssignableFrom(e.getClass())) return true;
        }
        return false;
    }
}
