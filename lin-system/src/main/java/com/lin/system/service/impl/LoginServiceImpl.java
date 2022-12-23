package com.lin.system.service.impl;

import com.lin.exception.CodeException;
import com.lin.exception.UserNamePassWordException;
import com.lin.system.dao.SysUserDao;
import com.lin.system.service.LoginService;
import com.lin.utils.JwtUtils;
import com.lin.utils.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public String login(Map<String, String> map) {
      String uuid =  map.containsKey("uuid")?map.get("uuid"):"";
      String username =  map.containsKey("username")?map.get("username"):"";
      String password =  map.containsKey("password")?map.get("password"):"";
      String code =  map.containsKey("code")?map.get("code"):"";

      if (StringUtils.isEmpty(username) | StringUtils.isEmpty(password)){
          throw new UserNamePassWordException();
      }

      if (StringUtils.isEmpty(uuid) | StringUtils.isEmpty(code)){
          throw new CodeException();
      }

      //判断  验证码
      String captcha = redisTemplate.opsForValue().get("captcha" + uuid).toString();
      if (!code.equals(captcha)){
          throw new CodeException();
      }

      //获取用户信息  查询用户权限
       LoginUser user =  loadUserByUsername(username,password);

      //生成uuid  并将用户对象放到redis 中
        String id = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("user"+id,user,2, TimeUnit.HOURS);
        return JwtUtils.getToken(id);
    }

    private LoginUser loadUserByUsername(String username,String password) {
        //通过用户名查询用户信息
        LoginUser user = sysUserDao.loadUserByUsername(username);
        if (user == null){
            throw new UserNamePassWordException();
        }
        //判断  密码是否正确
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new UserNamePassWordException();
        }
        //通过用户id 获取用户角色  通过用户角色  获取用户权限
        List<String> power = sysUserDao.getPower(user.getId());
        user.setAuthorities(power);
        return user;
    }
}
