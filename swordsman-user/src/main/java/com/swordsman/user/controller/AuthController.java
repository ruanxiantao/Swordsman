package com.swordsman.user.controller;

import com.swordsman.common.exception.StatusException;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import com.swordsman.user.util.JwtUtil;
import com.swordsman.user.vo.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author DuChao
 * @Date 2019-10-22 13:43
 * 认证 Controller
 * 用户注册、登录、三方登录...
 */
@Api(tags = "认证Api")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("账户密码登录")
    @PostMapping("/login")
    public ApiResult login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJwt(authentication, loginRequest.getRememberMe());
        return new ApiResult(jwt);
    }

    @PostMapping("/logout")
    public ApiResult logout(HttpServletRequest request){
        try {
            jwtUtil.invalidJwt(request);
        } catch (StatusException e){
            return new ApiResult(Status.UNAUTHORIZED);
        }
        return new ApiResult(Status.LOGOUT);
    }

}
