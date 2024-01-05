package org.sparta.hanghae99lv3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.hanghae99lv3.dto.TeacherRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private int career;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String phone;

    @Column(name = "teacher_intro", nullable = false)
    private String teacherIntro;

    public Teacher(TeacherRequestDto requestDto) {
        this.teacherName = requestDto.getTeacherName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phone = requestDto.getPhone();
        this.teacherIntro = requestDto.getTeacherIntro();
    }

    public void update(TeacherRequestDto requestDto) {
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phone = requestDto.getPhone();
        this.teacherIntro = requestDto.getTeacherIntro();
    }
}
