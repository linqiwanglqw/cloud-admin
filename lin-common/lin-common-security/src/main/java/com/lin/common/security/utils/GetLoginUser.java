package com.lin.common.security.utils;

import com.lin.exception.AuthException;
import com.lin.exception.TokenTimeOutException;
import com.lin.utils.JwtUtils;
import com.lin.utils.LoginUser;
import com.lin.utils.RequestUtlis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class GetLoginUser {


    private static RedisTemplate redisTemplate;


    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        GetLoginUser.redisTemplate = redisTemplate;
    }


    public static LoginUser getUser(){
        HttpServletRequest request = RequestUtlis.getRequest();
        // 获取token
        String token = request.getHeader("lin-token");
        if (StringUtils.isEmpty(token)){
            throw new AuthException();
        }else {
            //解析token
            String uuid = JwtUtils.getUserNameFormToken(token);
            if (redisTemplate.hasKey("user" + uuid)) {
                return (LoginUser) redisTemplate.opsForValue().get("user" + uuid);
            } else {
                throw new TokenTimeOutException();
            }
        }
    }


}
