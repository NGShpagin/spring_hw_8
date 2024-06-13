package gb.hw_8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class RecoverExceptionAspect {

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
            List<Class<? extends RuntimeException>> exceptions = extractExceptions(joinPoint);
            if (exceptions.contains(e.getClass())) {
                log.info("exception = [{}, {}]", e.getClass(), e.getMessage());
                throw e;
            }
            log.info("NO throwable exception - {}", e.getMessage());
            return null;
        }
    }

    public List<Class<? extends RuntimeException>> extractExceptions(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RecoverException annotation = methodSignature.getMethod().getAnnotation(RecoverException.class);
        return Arrays.stream(annotation.noRecoverFor()).toList();
    }
}
