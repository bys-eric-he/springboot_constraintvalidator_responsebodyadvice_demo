package com.validator.responsebodyadvice.demo.response;

import com.validator.responsebodyadvice.demo.common.CommonResult;
import com.validator.responsebodyadvice.demo.common.ConstantInterface;
import com.validator.responsebodyadvice.demo.common.ResultCode;
import com.validator.responsebodyadvice.demo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常捕获及返回值格式化处理
 * 我们需要定义一个统一的全局异常来捕获这些信息，并作为一种结果返回控制层。
 * 注解@ControllerAdvice 是一种作用于控制层的切面通知（Advice）,该注解能够将通用的@ExceptionHandler、@InitBinder和@ModelAttributes方法收集到一个类型，并应用到所有控制器上。
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    private final static Logger logger = LoggerFactory.getLogger(ResponseResultHandler.class);

    /**
     * 使用@ExceptionHandler注解捕获指定或自定义的异常；
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public ErrorResult handleBizExp(HttpServletRequest request, Exception ex) {
        logger.info("Business exception handler  " + ex.getMessage());
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            return new ErrorResult(String.valueOf(businessException.getCode()), businessException.getMessage(), true);
        } else {
            return new ErrorResult(ResultCode.FAILED);
        }
    }

    /**
     * 重写ResponseBodyAdvice接口方法, 检查是否请求了带@ResponseResult注解标记的API，有就会执行beforeBodyWrite方法，重写返回体，没有就直接返回。
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //String name = Objects.requireNonNull(methodParameter.getMethod()).getName();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult responseResult = (ResponseResult) request.getAttribute(ConstantInterface.RESPONSE_RESULT_NN);

        return responseResult != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            logger.info("response body write process...");
            if (body instanceof ErrorResult) {
                ErrorResult errorResult = (ErrorResult) body;
                //ResultCode resultCode = ResultCode.FAILED;
                return CommonResult.failed(errorResult.getMsg()).data(null);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            //ResultCode resultCode = ResultCode.FAILED;
            return CommonResult.failed(exception.getMessage()).data(null);
        }

        return CommonResult.success(body);
    }
}
