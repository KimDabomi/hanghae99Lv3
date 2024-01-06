package org.sparta.hanghae99lv3.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.dto.LoginRequestDto;
import org.sparta.hanghae99lv3.dto.StaffRequestDto;
import org.sparta.hanghae99lv3.entity.Staff;
import org.sparta.hanghae99lv3.entity.StaffAuthEnum;
import org.sparta.hanghae99lv3.jwt.JwtUtil;
import org.sparta.hanghae99lv3.message.ErrorMessage;
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

    public void createStaff(StaffRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String team = requestDto.getTeam();
        StaffAuthEnum auth = StaffAuthEnum.valueOf(requestDto.getAuth());

        checkEmailAndPwPattern(email, password);

        String encodedPassword = passwordEncoder.encode(password);
        Staff user = new Staff(email, encodedPassword, team, auth);
        staffRepository.save(user);
    }

    private void checkEmailAndPwPattern(String email, String password) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.EMAIL_FORMAT_ERROR_MESSAGE.getErrorMessage());
        }

        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!passwordMatcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.PASSWORD_VALIDATION_ERROR_MESSAGE.getErrorMessage());
        }
    }
}
