package org.sparta.hanghae99lv3.service;

import org.sparta.hanghae99lv3.dto.TeacherRequestDto;
import org.sparta.hanghae99lv3.dto.TeacherResponseDto;
import org.sparta.hanghae99lv3.entity.Teacher;
import org.sparta.hanghae99lv3.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Teacher getTeacher(Long id, TeacherRequestDto requestDto) {
        return findTeacher(id);
    }

    @Transactional
    public Teacher updateTeacher(Long id, TeacherRequestDto requestDto) {
        Teacher teacher = findTeacher(id);
        teacher.update(requestDto);

        return teacher;
    }

    public Long deleteTeacher(Long id) {
        Teacher teacher = findTeacher(id);
        teacherRepository.delete(teacher);

        return id;
    }

    private Teacher findTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 강사는 존재하지 않습니다.")
        );
    }
}
