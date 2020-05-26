package org.springbee.jdbc.dynamic.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springbee.jdbc.dynamic.DataSourceContextHolder;
import org.springbee.jdbc.dynamic.annotation.DynamicDataSource;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author zhanglei
 */
@Aspect
public class DynamicDataSourceAspect {

  @Around(value = "execution(* *..mapper*..*(..))")
  public Object getTargetDataSource(ProceedingJoinPoint point) throws Throwable {
    Object target = point.getTarget();
    MethodSignature signature = (MethodSignature) point.getSignature();

    DynamicDataSource dynamicDataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DynamicDataSource.class);

    if (dynamicDataSource == null) {
      dynamicDataSource = AnnotationUtils.findAnnotation(target.getClass(), DynamicDataSource.class);
    }

    if (dynamicDataSource == null) {
      return point.proceed();
    }

    Object result;
    try {
      DataSourceContextHolder.set(dynamicDataSource.value());
      result = point.proceed();
    } finally {
      DataSourceContextHolder.remove();
    }
    return result;
  }

}