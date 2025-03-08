package com.codeit.side.common.config;

import com.codeit.side.common.adapter.out.security.CustomUserDetails;
import com.codeit.side.common.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtWebSocketInterceptor implements HandshakeInterceptor {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        String query = request.getURI().getQuery();
        System.out.println("query = " + query);
        if (query == null || !query.contains("token=") || query.split("token=")[1].isEmpty()) {
            // 쿼리 파라미터가 없거나 'token'이 없는 경우
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false; // 핸드셰이크 중단
        }

        String jwt = query.split("&")[0].split("token=")[1].split("Bearer ")[1];

        try {
            String email = jwtUtil.getEmail(jwt);
            if (email == null) {
                return false;
            }

            if (jwtUtil.isExpired(jwt)) {
                throw new JwtException("잘못된 토큰입니다.");
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
            attributes.put("user", customUserDetails.getUser());
            return true;
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
