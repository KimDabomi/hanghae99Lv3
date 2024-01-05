package org.sparta.hanghae99lv3.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.dto.LoginRequestDto;
import org.sparta.hanghae99lv3.dto.StaffRequestDto;
import org.sparta.hanghae99lv3.entity.Staff;
import org.sparta.hanghae99lv3.entity.StaffAuthEnum;
import org.sparta.hanghae99lv3.jwt.JwtUtil;
import org.sparta.hanghae99lv3.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StaffService {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void createStaff(StaffRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String team = requestDto.getTeam();
        StaffAuthEnum auth = StaffAuthEnum.valueOf(requestDto.getAuth());

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }

        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!passwordMatcher.matches()) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Staff user = new Staff(email, encodedPassword, team, auth);
        staffRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 사용자 확인
        Staff staff = staffRepository.findByEmail(email);
        if (staff.getId() == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, staff.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(staff.getEmail(), staff.getAuth());
        jwtUtil.addJwtToCookie(token, res);
    }
}
