package com.validator.responsebodyadvice.demo.response;

import com.validator.responsebodyadvice.demo.common.ConstantInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截API请求,判断Response返回值是否需要统一格式化处理
 */
public class ResponseResultInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ResponseResultInterceptor.class);

    /**
     * 该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，
     * 当返回值为true 时就会继续调用下一个Interceptor的preHandle 方法，
     * 如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法
     * <p>
     * 在业务处理器处理请求之前被调用 如果返回false
     * * 从当前的拦截器往回执行所有拦截器的afterCompletion(),
     * * 再退出拦截器链, 如果返回true 执行下一个拦截器,
     * * 直到所有的拦截器都执行完毕 再执行被拦截的Controller
     * * 然后进入拦截器链,
     * * 从最后一个拦截器往回执行所有的postHandle()
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();

            if (clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(ConstantInterface.RESPONSE_RESULT_NN, clazz.getAnnotation(ResponseResult.class));
                logger.info("Class [{}] Url [{}] must return RESPONSE_RESULT_NN Data Format before response..", clazz.getName(), request.getRequestURI());
            } else if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(ConstantInterface.RESPONSE_RESULT_NN, method.getAnnotation(ResponseResult.class));
                logger.info("Method [{}] Url [{}] must return RESPONSE_RESULT_NN Data Format before response..", method.getName(), request.getRequestURI());
            }

        }

        // 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }
}
