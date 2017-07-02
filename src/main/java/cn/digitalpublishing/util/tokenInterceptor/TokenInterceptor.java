package cn.digitalpublishing.util.tokenInterceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.digitalpublishing.util.tokenInterceptor.TokenAnnotation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 防止重复提交的过滤器
 * @author chensr
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{

	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
        if (handler instanceof HandlerMethod) {  
            HandlerMethod handlerMethod = (HandlerMethod) handler;  
            Method method = handlerMethod.getMethod();  
            TokenAnnotation annotation = method.getAnnotation(TokenAnnotation.class);  
            if (annotation != null) {  
                boolean needSaveSession = annotation.save();  
                if (needSaveSession) {  
                    request.getSession(false).setAttribute("token", IDUtil.uuid2());  
                }  
                boolean needRemoveSession = annotation.remove();  
                if (needRemoveSession) {  
                    if (isRepeatSubmit(request)) {  
                        return false;  
                    }  
                    request.getSession(false).removeAttribute("token");  
                }  
            }  
            return true;  
        } else {  
            return super.preHandle(request, response, handler);  
        }  
    }  
  
    private boolean isRepeatSubmit(HttpServletRequest request) {  
        String serverToken = (String) request.getSession(false).getAttribute("token");  
        if (serverToken == null) {  
            return true;  
        }  
        String clinetToken = request.getParameter("token");  
        if (clinetToken == null) {  
            return true;  
        }  
        if (!serverToken.equals(clinetToken)) {  
            return true;  
        }  
        return false;  
    }  
}
