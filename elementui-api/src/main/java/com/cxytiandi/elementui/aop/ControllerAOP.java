package com.cxytiandi.elementui.aop;

import com.cxytiandi.elementui.base.ResponseData;
import com.cxytiandi.elementui.exceptions.CheckException;
import com.cxytiandi.elementui.exceptions.NoPermissionException;
import com.cxytiandi.elementui.exceptions.UnloginException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 处理和包装异常
 * 
 * @author 晓风轻 https://github.com/xwjie/PLMCodeTemplate
 */


@Aspect
@Component
@EnableAspectJAutoProxy
public class ControllerAOP {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);
	@Pointcut("execution(public * com.cxytiandi.elementui.controller..*.*(..))")
	public void pointcut() {
	}

	@Before("pointcut()")
	public Object handlerControllerMethod(JoinPoint joinPoint) {
		long startTime = System.currentTimeMillis();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		ResponseData<?> result = null;

		try {

			// 如果需要打印入参，可以从这里取出打印
			// Object[] args = pjp.getArgs();

			// 本次操作用时（毫秒）

			//url
			logger.info("url={}", request.getRequestURI());

			//method
			logger.info("method={}", request.getMethod());

			//ip
			logger.info("ip={}", request.getRemoteAddr());

			//method
			logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

			//param
			long elapsedTime = System.currentTimeMillis() - startTime;
			logger.info("args={}", joinPoint.getArgs());
			logger.info("[{}]use time: {}", joinPoint.getSignature(), elapsedTime);
		} catch (Throwable e) {
			result = handlerException(joinPoint, e);
		}

		return result;
	}

	private ResponseData<?> handlerException(JoinPoint pjp, Throwable e) {
		ResponseData<?> result = new ResponseData();

		// 已知异常【注意：已知异常不要打印堆栈，否则会干扰日志】
		// 校验出错，参数非法
		if (e instanceof CheckException || e instanceof IllegalArgumentException) {
			result.setMessage(e.getLocalizedMessage());
			result.setCode(ResponseData.CHECK_FAIL);
		}
		// 没有登陆
		else if (e instanceof UnloginException) {
			result.setMessage("Unlogin");
			result.setCode(ResponseData.NO_LOGIN);
		}
		// 没有权限
		else if (e instanceof NoPermissionException) {
			result.setMessage("NO PERMISSION");
			result.setCode(ResponseData.NO_PERMISSION);
		} else {
			logger.error(pjp.getSignature() + " error ", e);

			// TODO 未知的异常，应该格外注意，可以发送邮件通知等
			result.setMessage(e.toString());
			result.setCode(ResponseData.UNKNOWN_EXCEPTION);
		}

		return result;
	}
}
