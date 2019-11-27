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
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    private final static Logger logger = LoggerFactory.getLogger(ResponseResultHandler.class);

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
     * 检查是否请求了带@ResponseResult注解标记的API，有就重写返回体，没有就直接返回。
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
                return CommonResult.failed(errorResult.getMsg());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            //ResultCode resultCode = ResultCode.FAILED;
            return CommonResult.failed(exception.getMessage());
        }

        return CommonResult.success(body);
    }
}
