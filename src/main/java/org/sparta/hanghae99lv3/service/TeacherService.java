package org.sparta.hanghae99lv3.service;

import org.sparta.hanghae99lv3.dto.TeacherRequestDto;
import org.sparta.hanghae99lv3.dto.TeacherResponseDto;
import org.sparta.hanghae99lv3.entity.Teacher;
import org.sparta.hanghae99lv3.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherResponseDto createTeacher(TeacherRequestDto requestDto) {
        Teacher teacher = new Teacher(requestDto);
        Teacher saveTeacher = teacherRepository.save(teacher);
        return new TeacherResponseDto(saveTeacher);
    }

}
