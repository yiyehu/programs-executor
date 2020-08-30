package tech.yiyehu.executor.common.handler;

import tech.yiyehu.executor.common.utils.ResultException;
import tech.yiyehu.executor.common.utils.ResultUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class ResultExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(ResultException.class)
	public ResultUtil handleRRException(ResultException e){
		ResultUtil resultUtil = new ResultUtil();
		resultUtil.put("code", e.getCode());
		resultUtil.put("msg", e.getMessage());

		return resultUtil;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResultUtil handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return ResultUtil.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResultUtil handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return ResultUtil.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResultUtil handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return ResultUtil.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public ResultUtil handleException(Exception e){
		logger.error(e.getMessage(), e);
		return ResultUtil.error();
	}
}
