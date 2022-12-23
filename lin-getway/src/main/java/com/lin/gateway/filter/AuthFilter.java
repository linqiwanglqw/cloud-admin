package com.lin.gateway.filter;


import com.alibaba.fastjson.JSON;
import com.lin.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * 网关鉴权
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    //不需要被拦截的接口
    private static final String[] URL_WHITELIST = {
            "/system/captchaImage",
            "/system/login"

    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request
        ServerHttpRequest request = exchange.getRequest();
        //获取当前访问的路径
        String path = request.getURI().getPath();

        if (Arrays.asList(URL_WHITELIST).contains(path)) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("lin-token");
        //开始判断是否存在token
        if (StringUtils.isEmpty(token)) {
            //返回状态
            return writer(exchange.getResponse());
        }
        return chain.filter(exchange);
    }

    private Mono<Void> writer(ServerHttpResponse response) {
        //状态码
        response.setStatusCode(HttpStatus.OK);
        //返回数据格式
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(R.unauthz()).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
