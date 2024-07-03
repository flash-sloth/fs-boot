package top.fsfsfs.main.logsys.aspect;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import top.fsfsfs.basic.utils.ContextUtil;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.config.properties.SystemProperties;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import top.fsfsfs.main.logsys.entity.FsSysLogs;
import top.fsfsfs.main.logsys.service.FsSysLogsService;
import top.fsfsfs.main.logsys.util.RequestHolder;


/**
 * 操作日志使用spring event异步入库
 *
 * @author zuihou
 * @since 2019-07-01 15:15
 */
@Component  //实现bean的注入
@Slf4j
@Aspect
@RequiredArgsConstructor  //代替@Autowired注解
@EnableAsync
public class FsLogAspect {

    private final SystemProperties systemProperties;
    private final FsSysLogsService fSysLogsService;

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

    /**
     * 服务层的切入规则
     */
    @Pointcut(value  = """
            execution(* top.fsfsfs..service..*.*(..) ) &&
            execution(public * *(..) )
            """)
    public void fsLogAspectService() {

    }

    /**
     * 控制层的切入规则
     */
    @Pointcut(value  = """
            execution(* top.fsfsfs..controller..*.*(..) ) &&
            execution(public * *(..) )
            """)
    public void fsLogAspectController() {

    }

    /**
     * 控制层连接点 环绕通知
     * 控制层操日志 写入到 数据库中
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("fsLogAspectController()")
    public Object invokeController(ProceedingJoinPoint joinPoint) throws Throwable {

        String logTraceId = ContextUtil.getLogTraceId() == null ? StrPool.EMPTY : ContextUtil.getLogTraceId();
        String types = getParamTypes(joinPoint);

        outArgsLog(joinPoint, logTraceId, types, joinPoint.getArgs(), systemProperties.getRecordArgs());

        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed(); //执行方法
            //写入到数据库中
            //outResultLog(joinPoint, logTraceId, types, startTime, result, systemProperties.getRecordResult());
            logToDb(joinPoint, logTraceId, types, startTime, result, systemProperties.getRecordResult());
        } catch (Exception e) {
            log.error("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), types, System.currentTimeMillis() - startTime, e);
            throw e;
        }finally {
            long time = System.currentTimeMillis() - startTime;
        }
        return result;
    }

    /**
     * 服务层连接点 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("fsLogAspectService()")
    public Object invokeService(ProceedingJoinPoint joinPoint) throws Throwable {

        String logTraceId = ContextUtil.getLogTraceId() == null ? StrPool.EMPTY : ContextUtil.getLogTraceId();
        String types = getParamTypes(joinPoint);

        outArgsLog(joinPoint, logTraceId, types, joinPoint.getArgs(), systemProperties.getRecordArgs());

        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed(); //执行方法
            //写入到数据库中
            outResultLog(joinPoint, logTraceId, types, startTime, result, systemProperties.getRecordResult());
        } catch (Exception e) {
            log.error("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), types, System.currentTimeMillis() - startTime, e);
            throw e;
        }finally {
            long time = System.currentTimeMillis() - startTime;
        }
        return result;
    }

    /**
     * 把日志写入到数据库
     * @param joinPoint
     * @param logTraceId
     * @param types
     * @param start
     * @param retVal
     * @param flag
     *
     * @author dely
     * @since 2024-06-30 22:15
     */

    public  void logToDb(ProceedingJoinPoint joinPoint, String logTraceId, String types, long start, Object retVal, boolean flag) {
        /**
         synchronized
        log.info("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), types, System.currentTimeMillis() - start);
        */

        FsSysLogs fsSysLogs =new FsSysLogs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        Method method = signature.getMethod();
        joinPoint.getArgs();
        //获取方法上的注解
        Operation operation = method.getAnnotation(Operation.class);
        if(operation != null){
            fsSysLogs.setTags( JSONUtil.toJsonPrettyStr( operation.tags() ));
            fsSysLogs.setLogDescription( operation.description() );//描述
            fsSysLogs.setLogType( operation.operationId() );//操作ID
        }
        fsSysLogs.setLogIp( RequestHolder.getIp2() );
        fsSysLogs.setUserAgent( RequestHolder.getBrowser() );
        fsSysLogs.setExecuteTime( Long.valueOf(System.currentTimeMillis() - start).intValue() );
        fsSysLogs.setLogMethod( className + "." + methodName + "()");

        //请求的参数

        String strParams = null;
        try {
            strParams = getParameter(joinPoint, methodName, RequestHolder.getHttpServletRequest());
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        } catch (NotFoundException e) {
            //e.printStackTrace();
        }
        fsSysLogs.setLogParams(strParams);
/**
        Object[] args = joinPoint.getArgs();
        String params =  JSONUtil.toJsonPrettyStr(args);
        if (params.length() < 4000) {
            log.info(params);
            fsSysLogs.setLogParams(params);
        }
*/

        //返回值
        String jsonRetVal =  JSONUtil.toJsonPrettyStr(retVal);
        if (jsonRetVal.length() < 4000) {
            fsSysLogs.setExceptionDetail(jsonRetVal);
        }

        //是否记录返回值
        if (flag) {
            log.info("<<<< [traceId:{}] result={}", logTraceId, retVal);
        }

        fsSysLogs.setId(IdUtil.getSnowflakeNextId());
        fSysLogsService.saveAsync( fsSysLogs );
    }


    /**
     * 获取参数
     *
     * @param joinPoint
     * @param methodName 方法名
     * @param request
     * @return
     * @throws ClassNotFoundException
     *
     */
    private String getParameter(ProceedingJoinPoint joinPoint, String methodName, HttpServletRequest request) throws ClassNotFoundException,NotFoundException {
        Object[] params = joinPoint.getArgs();//参数值
        String classType = joinPoint.getTarget().getClass().getName();//获取操作菜单名字
        Class<?> clazz = Class.forName(classType);//获取操作菜单名字class
        String clazzName = clazz.getName();//获取操作菜单名字
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, params);

        return JSONUtil.toJsonStr (nameAndArgs);
    }

    /**
     * 获取路径名称
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();//默认的类搜索路径
        ClassClassPath classPath = new ClassClassPath(cls);//获取一个ctClass对象
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);//class文件的抽象表示
        CtMethod cm = cc.getDeclaredMethod(methodName);//获取名称
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        //处理参数
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }
}
