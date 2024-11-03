package com.shxy.sudatqd.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shxy.models.paramsBean.User;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @description jwt工具
 */
public class JwtUtil {
    /**
     * 获取token
     *
     * @param u user
     * @return token
     */
    public static String getToken(User u, String sign) {
        Calendar instance = Calendar.getInstance();
        //默认令牌过期时间7天
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("username", u.getUsername()).withClaim("password", u.getUserpass());

        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 验证token合法性 成功返回token
     */
    public static DecodedJWT verify(String token, String jwtSign) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new Exception("token不能为空");
        }
        JWTVerifier build = JWT.require(Algorithm.HMAC256(jwtSign)).build();
        return build.verify(token);
    }

    /**
     * 根据token返回指定的信息
     */
    public static String decodeInfo(String token) {
        Map<String, Claim> params = JWT.decode(token).getClaims();
        return params.get("username").asString();
    }
}
