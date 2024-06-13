package gb.hw_8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class TimerAspect {

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
}
