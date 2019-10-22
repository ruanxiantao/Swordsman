package com.swordsman.user.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.swordsman.common.exception.StatusException;
import com.swordsman.common.redis.RedisUtil;
import com.swordsman.common.web.Status;
import com.swordsman.user.config.JwtConfig;
import com.swordsman.user.constants.Const;
import com.swordsman.user.vo.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author DuChao
 * @Date 2019-10-22 09:46
 * Jwt 工具类
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建 Jwt
     * @param rememberMe    记住我
     * @param id            用户Id
     * @param subject       用户名
     * @param roles         用户角色
     * @param authorities   用户权限
     * @return              JWT
     */
    public String createJwt(Boolean rememberMe, String id, String subject,
                            List<String> roles, Collection<? extends GrantedAuthority> authorities){
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,jwtConfig.getKey())
                .claim("roles",roles)
                .claim("authorities",authorities);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0)
            builder.setExpiration(DateUtil.offsetMillisecond(now,ttl.intValue()));

        String jwt = builder.compact();
        // 将 Jwt 放入Redis
        redisUtil.setEx(Const.JWT_PREFIX + subject,jwt,ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建 Jwt
     * @param authentication    用户认证信息
     * @param rememberMe        记住我
     * @return                  JWT
     */
    public String createJwt(Authentication authentication,Boolean rememberMe){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createJwt(rememberMe,userPrincipal.getId(),userPrincipal.getUsername(),userPrincipal.getRoles(),userPrincipal.getAuthorities());
    }

    /**
     * 解析 JWT
     * Claims 继承 Map接口
     * @param jwt
     * @return
     */
    public Claims parseJwt(String jwt){

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = Const.JWT_PREFIX + username;

            // 校验 redis 中的Jwt是否存在
            Long ttl = redisUtil.getExpire(redisKey);
            if (Objects.isNull(ttl) || ttl <= 0)
                throw new StatusException(Status.TOKEN_EXPIRED);

            // 校验 redis 中的 Jwt 是否与当前一致，不一致则代表用户已注销或用户在不同设备登录，均代表 JWT 过期
            String redisJwt = redisUtil.get(redisKey).toString();
            if (!StrUtil.equals(jwt,redisJwt))
                throw new StatusException(Status.TOKEN_OUT_OF_CTRL);

            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new StatusException(Status.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new StatusException(Status.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new StatusException(Status.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new StatusException(Status.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new StatusException(Status.TOKEN_PARSE_ERROR);
        }

    }

    /**
     * 设置 Jwt 过期
     */
    @Async
    public void invalidJwt(HttpServletRequest request){
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJwt(jwt);
        redisUtil.delete(Const.JWT_PREFIX + username);
    }

    /**
     * 根据 jwt 获取用户名
     */
    public String getUsernameFromJwt(String jwt){
        Claims claims = parseJwt(jwt);
        return claims.getSubject();
    }
    /**
     * 从 request 的 header 中获取 Jwt
     */
    public String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

}
