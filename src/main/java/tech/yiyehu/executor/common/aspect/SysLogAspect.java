package tech.yiyehu.executor.common.aspect;

import org.springframework.util.StopWatch;
import tech.yiyehu.executor.common.annotation.SysLog;
import tech.yiyehu.executor.common.utils.WatchTimeUtil;
import tech.yiyehu.executor.modules.sys.entity.SysLogEntity;
import tech.yiyehu.executor.modules.sys.entity.SysUserEntity;
import tech.yiyehu.executor.modules.sys.service.SysLogService;
import com.google.gson.Gson;
import tech.yiyehu.executor.common.utils.HttpContextUtils;
import tech.yiyehu.executor.common.utils.IPUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志记录，记录至数据库
 */
@Aspect
@Component
public class SysLogAspect {

    private final SysLogService sysLogService;

    public SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Around("@annotation(tech.yiyehu.executor.common.annotation.SysLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        WatchTimeUtil watch = WatchTimeUtil.instance().start();
        Object result = point.proceed();
        saveSysLog(point, watch.stop().getInterval());
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args);
            sysLog.setParams(params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());
        //保存系统日志
        sysLogService.save(sysLog);
    }
}
