package com.lin.system.controller;

import com.lin.common.security.utils.GetLoginUser;
import com.lin.system.service.LoginService;
import com.lin.utils.LoginUser;
import com.lin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @PostMapping
    public Object login(@RequestBody Map<String,String> map){
        //登录
        if (CollectionUtils.isEmpty(map)){
                //判断如果为空
            return R.badArgument();
        }
        return R.ok(loginService.login(map));
    }


    @GetMapping
    public Object getlogin(){
        LoginUser user = GetLoginUser.getUser();
        return R.ok(user);
    }

}
