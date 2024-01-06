package org.sparta.hanghae99lv3.service;

import org.sparta.hanghae99lv3.dto.TeacherRequestDto;
import org.sparta.hanghae99lv3.dto.TeacherResponseDto;
import org.sparta.hanghae99lv3.entity.Lecture;
import org.sparta.hanghae99lv3.entity.Teacher;
import org.sparta.hanghae99lv3.message.ErrorMessage;
import org.sparta.hanghae99lv3.message.SuccessMessage;
import org.sparta.hanghae99lv3.repository.LectureRepository;
import org.sparta.hanghae99lv3.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;

    public TeacherService(TeacherRepository teacherRepository, LectureRepository lectureRepository) {
        this.teacherRepository = teacherRepository;
        this.lectureRepository = lectureRepository;
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

    public ResponseEntity<String> deleteTeacher(Long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if(optionalTeacher.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EXIST_TEACHER_ERROR_MESSAGE.getErrorMessage());
        }

        Teacher teacher = optionalTeacher.get();
        List<Lecture> lectures = lectureRepository.findByTeacher(teacher);
        lectureRepository.deleteAll(lectures);
        teacherRepository.delete(teacher);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessMessage.DELETE_SUCCESS_MESSAGE.getSuccessMessage());
    }

    private Teacher findTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.EXIST_TEACHER_ERROR_MESSAGE.getErrorMessage())
        );
    }
}
