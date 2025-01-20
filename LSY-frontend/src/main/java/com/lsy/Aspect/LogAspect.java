package com.lsy.Aspect;

import com.alibaba.fastjson.JSON;
import com.lsy.annotation.SystempLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    //确定切点
    @Pointcut("@annotation(com.lsy.annotation.SystempLog)")
    public void pointCut(){

    }

    //环绕通知方法
    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            //方法前置日志打印
            handlerBefore(joinPoint);
            ret = joinPoint.proceed();
            //方法处理后日志打印
            hanlerAfter(ret);
        } finally {
//            结束后换行
            log.info("===================End==================="+System.lineSeparator());

        }
        return ret;
    }



    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystempLog systempLog = getSystempLog(joinPoint);
        log.info(System.lineSeparator());
        log.info("======================== Start ========================");
// 打印请求 URL
        log.info("URL : {}",request.getRequestURL());
// 打印描述信息
        log.info("BusinessName : {}", systempLog.businessName());
// 打印 Http method
        log.info("HTTP Method : {}",request.getMethod() );
// 打印调用 controller 的全路径以及执行方法
        log.info("Class Method : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
// 打印请求的 IP
        log.info("IP : {}",request.getRemoteHost());
// 打印请求入参
        log.info("Request Args : {}", JSON.toJSONString(joinPoint.getArgs()));

    }

    private void hanlerAfter(Object ret) {
        // 打印出参
        log.info("Response : {}", JSON.toJSONString(ret));

    }

    private SystempLog getSystempLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystempLog systempLog = signature.getMethod().getAnnotation(SystempLog.class);
        return systempLog;
    }

}
