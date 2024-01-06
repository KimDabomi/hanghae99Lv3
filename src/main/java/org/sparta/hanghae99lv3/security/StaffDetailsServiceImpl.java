package org.sparta.hanghae99lv3.security;

import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.entity.Staff;
import org.sparta.hanghae99lv3.message.ErrorMessage;
import org.sparta.hanghae99lv3.repository.StaffRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffDetailsServiceImpl implements UserDetailsService {
    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByEmail(email);
        if (staff == null) {
            throw new UsernameNotFoundException(ErrorMessage.EXIST_STAFF_ERROR_MESSAGE.getErrorMessage());
        }

        return new StaffDetailsImpl(staff);
    }
}