package ecnic.service.configs;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Aspect
@Slf4j
public class LogAspect {

    @PostConstruct
    void initialization(){
        log.info("Initialization Log Aspect Success!!");
    }
    @Pointcut("execution(* ecnic.document.services.impl.*.*(..))")
    public void pointCutServiceImpl() {
    }

    @Around("pointCutServiceImpl()")
    public Object adviceMethod(ProceedingJoinPoint jp) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();
        String classNameWithPackage = jp.getTarget().getClass().getName();
        String methodClassName = jp.getSignature().getName();
        List<Object> parameter = Arrays.asList(jp.getArgs());
        String duration = null;
        try {
            log.info("Call: {}.{}", classNameWithPackage, methodClassName);
            log.info("Args: {}", parameter);
            return jp.proceed();
        } finally {
            duration = getDuration(startTime, LocalDateTime.now());
            log.info("Time Execution: {}", duration);
        }

    }

    private String getDuration(LocalDateTime start, LocalDateTime end) {
        LocalDateTime tempDateTime = LocalDateTime.from(start);
        long minutes = tempDateTime.until(end, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);
        long seconds = tempDateTime.until(end, ChronoUnit.SECONDS);

        return minutes + " minutes " + seconds + " seconds.";
    }

}
