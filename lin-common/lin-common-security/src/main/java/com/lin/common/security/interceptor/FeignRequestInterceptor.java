package com.lin.common.security.interceptor;

import com.lin.utils.RequestUtlis;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = RequestUtlis.getRequest();
        if (request != null) {
            String token = request.getHeader("lin-token");
            requestTemplate.header("lin-token", token);
        }
    }
}
