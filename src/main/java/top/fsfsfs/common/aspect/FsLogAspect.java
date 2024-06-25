package top.fsfsfs.common.aspect;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import top.fsfsfs.basic.utils.ContextUtil;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.config.properties.SystemProperties;

/**
 * 操作日志使用spring event异步入库
 *
 * @author zuihou
 * @since 2019-07-01 15:15
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class FsLogAspect {
    private final SystemProperties systemProperties;

    protected String getParamTypes(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature ms) {
            Class<?>[] parameterTypes = ms.getParameterTypes();
            if (parameterTypes == null) {
                return "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (Class<?> cls : parameterTypes) {
                    if (sb.length() > 0) {
                        sb.append(", ").append(cls.getSimpleName());
                    } else {
                        sb.append(cls.getSimpleName());
                    }
                }
                return sb.toString();
            }
        }
        return "";
    }


    protected void outArgsLog(ProceedingJoinPoint joinPoint, String logTraceId, String types, Object[] args, boolean flag) {
        log.info(">>>> [traceId:{}] {}.{}({}) start...", logTraceId, joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), types);
        if (flag) {
            log.info(">>>> [traceId:{}] args={}", logTraceId, args);
        }
    }

    protected void outResultLog(ProceedingJoinPoint joinPoint, String logTraceId, String types, long start, Object retVal, boolean flag) {
        log.info("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), types, System.currentTimeMillis() - start);
        if (flag) {
            log.info("<<<< [traceId:{}] result={}", logTraceId, retVal);
        }
    }

    /***
     * 定义controller切入点拦截规则：拦截标记SysLog注解和指定包下的方法
     * 2个表达式加起来才能拦截所有Controller 或者继承了BaseController的方法
     *
     * execution(public * top.fsfsfs.boot.*.*(..)) 解释：
     * 第一个* 任意返回类型
     * 第二个* 包下的所有类
     * 第三个* 类下的所有方法
     * ()中间的.. 任意参数
     *
     */
    @Pointcut("""
            execution(* top.fsfsfs.boot.controller..*.*(..))) ||
            execution(* top.fsfsfs.boot.service..*.*(..))) ||
            execution(* top.fsfsfs.boot.biz..*.*(..)))
            """)
    public void fsLogAspect() {

    }

    @Around("fsLogAspect()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String logTraceId = ContextUtil.getLogTraceId() == null ? StrPool.EMPTY : ContextUtil.getLogTraceId();
        String types = getParamTypes(joinPoint);

        outArgsLog(joinPoint, logTraceId, types, joinPoint.getArgs(), systemProperties.getRecordArgs());
        long start = System.currentTimeMillis();
        try {
            Object retVal = joinPoint.proceed();
            outResultLog(joinPoint, logTraceId, types, start, retVal, systemProperties.getRecordResult());
            return retVal;
        } catch (Exception e) {
            log.error("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), types, System.currentTimeMillis() - start, e);
            throw e;
        }
    }

}
