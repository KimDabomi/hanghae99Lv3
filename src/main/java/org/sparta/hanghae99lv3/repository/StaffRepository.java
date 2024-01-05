package org.sparta.hanghae99lv3.repository;

import org.sparta.hanghae99lv3.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
}
