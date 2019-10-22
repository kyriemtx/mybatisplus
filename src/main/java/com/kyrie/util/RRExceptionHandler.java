package com.kyrie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public BaseResult handleRRException(RRException e){
		BaseResult baseResult = new BaseResult();
		baseResult.put("code", e.getCode());
		baseResult.put("msg", e.getMessage());

		return baseResult;
	}

	@ExceptionHandler(Exception.class)
	public BaseResult handleException(Exception e){
		logger.error(e.getMessage(), e);
		return BaseResult.error();
	}
}
