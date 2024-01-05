package org.sparta.hanghae99lv3.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.sparta.hanghae99lv3.entity.Staff;
import org.sparta.hanghae99lv3.entity.StaffAuthEnum;
import org.sparta.hanghae99lv3.jwt.JwtUtil;
import org.sparta.hanghae99lv3.repository.StaffRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {
    private final StaffRepository staffRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(StaffRepository staffRepository, JwtUtil jwtUtil) {
        this.staffRepository = staffRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) && (url.startsWith("/api/login") || url.startsWith("/api/join"))) {
            chain.doFilter(request, response);
        }

        String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

        if (!StringUtils.hasText(tokenValue)) {
            throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
        }

        String token = jwtUtil.substringToken(tokenValue);

        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
        }

        Claims info = jwtUtil.getUserInfoFromToken(token);

        Staff staff = staffRepository.findByEmail(info.getSubject());
        if (staff.getId() == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        if (!StaffAuthEnum.ADMIN.equals(staff.getAuth())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        request.setAttribute("staff", staff);
        chain.doFilter(request, response);
    }
}