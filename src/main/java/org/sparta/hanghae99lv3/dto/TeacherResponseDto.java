package org.sparta.hanghae99lv3.dto;

import lombok.Getter;
import org.sparta.hanghae99lv3.entity.Teacher;

@Getter
public class TeacherResponseDto {
    private Long id;
    private String teacherName;
    private int career;
    private String company;
    private String phone;
    private String teacherIntro;

    public TeacherResponseDto(Teacher teacher) {
        this.id = teacher.getId();
        this.teacherName = teacher.getTeacherName();
        this.career = teacher.getCareer();
        this.company = teacher.getCompany();
        this.phone = teacher.getPhone();
        this.teacherIntro = teacher.getTeacherIntro();
    }
}
