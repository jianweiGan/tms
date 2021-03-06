package com.intercept;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.chinaway.tms.utils.RequestUtil;

public class CommonInterceptor implements HandlerInterceptor {  
	private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);  
    public static final String LAST_PAGE = "com.alibaba.lastPage";  
    
    @SuppressWarnings({ "unchecked" })
	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {  
    	if ("GET".equalsIgnoreCase(request.getMethod())) {  
            RequestUtil.saveRequest(request);  
        }  
        log.info("==============执行顺序: 1、preHandle================");    
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        
        log.info("requestUri:"+requestUri);    
        log.info("contextPath:"+contextPath);    
        log.info("url:"+url);    
          
        String username =  (String)request.getSession().getAttribute("username");   
        if(username == null){
            log.info("Interceptor：跳转到login页面！"); 
            response.setStatus(0);
//            response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/login.htm");
            response.getWriter().println("<script>window.location.href='/login.html';</script>");
            response.getWriter().flush();
            response.getWriter().close();
            return false;  
        }else{
//        	if("index.html".equals(requestUri)){
//        		return true;
//        	}
//        	List<Map<String, Object>> sysMenuMap = (List<Map<String, Object>>) request.getSession().getAttribute("sysMenu");
//            boolean isValied = false;
//            for(Map<String,Object> map :sysMenuMap){
//				Object resUrl = map.get("resUrl");
//				if (null == resUrl || StringUtils.isEmpty(String.valueOf(resUrl)) || "#".equals(String.valueOf(resUrl))) {
//					continue;
//				}
//				String startUri = URLDecoder.decode(requestUri.substring(0, requestUri.lastIndexOf(".")), "UTF-8");
//				String uriFormDb = String.valueOf(resUrl).replace("#", "/");
//				String startUriFormDb = uriFormDb.substring(0, uriFormDb.lastIndexOf("."));
//				
//				if (startUri.indexOf(startUriFormDb) != -1) {
//					isValied = true;
//				}
//            }
//            
//            // 没有访问该地址的权限
//            if(!isValied){
//            	response.getWriter().println("<script>window.location.href='/noPermission.html';</script>");
//            	response.getWriter().flush();
//                response.getWriter().close();
//                return false;
//            }else
        	return true;  
        }
    }  
  
    @Override  
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {  
//    	log.info("==============执行顺序: 2、postHandle================");    
//        if(modelAndView != null){  //加入当前时间    
//            modelAndView.addObject("var", "测试postHandle");    
//        }    
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) throws Exception {  
//    	log.info("==============执行顺序: 3、afterCompletion================");
    }  
  
  
    /**  
     * @param request  
     * @return Create Date:2013-6-5  
     * @author Shine  
     * Description:获取IP  
     */  
    @SuppressWarnings("unused")
	private String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
}  
