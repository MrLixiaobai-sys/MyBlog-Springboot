package com.lsy.Aspect;

import com.alibaba.fastjson.JSON;
import com.lsy.annotation.SystempLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

//    定义切点
//    这个切点会匹配所有使用了 @SystempLog 注解的方法
//    pointCut()切点的标识
    @Pointcut("@annotation(com.lsy.annotation.SystempLog)")
    public void pointCut(){

    }

    //环绕通知方法
//    ProceedingJoinPoint 是 JoinPoint 的子接口，允许在通知中继续执行目标方法。它是连接点的一个实例，可以访问到方法签名、方法参数、执行目标等信息。
    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            //对应方法前置日志打印
            handlerBefore(joinPoint);

            //执行对应方法
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
//        获取当前请求的上下文
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

//        根据当前请求获取当前 HTTP 请求对象
        HttpServletRequest request = requestAttributes.getRequest();

//        获取被增强方法上的注解对象
//        通过反射获取目标方法上的 @SystempLog 注解，获取 businessName 等属性
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
