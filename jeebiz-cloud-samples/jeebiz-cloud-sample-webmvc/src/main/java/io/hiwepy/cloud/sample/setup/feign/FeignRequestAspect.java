package io.hiwepy.cloud.sample.setup.feign;

import com.alibaba.fastjson.JSON;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.sample.exception.BizExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FeignRequestAspect {

    @Pointcut("execution(* com.wans.**.feign.client..*.*(..))")
    public void pointcut(){}
    
    @SuppressWarnings("rawtypes")
	@Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        try{
            Object proceed = proceedingJoinPoint.proceed();
            ApiRestResponse apiResponse = (ApiRestResponse) proceed;
            if (apiResponse.getCode() != 200) {
            	BizExceptionCode.throwByErrorcode(apiResponse.getCode());
            }
            log.info("remote call response:{}", JSON.toJSONString(proceed));
            return proceed;
        } catch (Throwable e) {
            log.error("remote call error", e);
            throw BizExceptionCode.REMOTE_INVOKE_ERROR.asException();
        }
    }

}
