package com.oa.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author oa 装饰器模式
 * @since on 2018/5/10.
 */
public interface AspectApi {

     Object doHandlerAspect(ProceedingJoinPoint pjp, Method method)throws Throwable;

}
