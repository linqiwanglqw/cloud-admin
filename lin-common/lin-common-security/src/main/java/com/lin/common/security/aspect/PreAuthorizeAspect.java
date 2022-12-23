package com.lin.common.security.aspect;

import com.lin.common.security.annotation.RequiresPermissions;
import com.lin.common.security.utils.GetLoginUser;
import com.lin.exception.AuthException;
import com.lin.utils.LoginUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class PreAuthorizeAspect {


    /**
     * 构建
     */
    public PreAuthorizeAspect() {
    }
    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.lin.common.security.annotation.RequiresPermissions)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    /**
     * 环绕切入
     *
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        try {
            // 执行原有逻辑
            Object obj = joinPoint.proceed();
            return obj;
        } catch (Throwable e) {

            throw e;
        }
    }


    /**
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method) {

        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        //权限内容
        System.out.println(requiresPermissions.value());
        if (requiresPermissions != null) {
            doCheckPermissions(requiresPermissions);
        }
    }


    //检查权限 如果没有权限 就抛出异常信息

    private void doCheckPermissions(RequiresPermissions requiresPermissions){
        String value = requiresPermissions.value();  //权限信息
        LoginUser user = GetLoginUser.getUser();
        if (!user.getAuthorities().contains(value)){
            throw new AuthException();
        }
    }

}
