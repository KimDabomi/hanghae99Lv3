package org.sparta.hanghae99lv3.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.dto.LectureRequestDto;
import org.sparta.hanghae99lv3.dto.LectureResponseDto;
import org.sparta.hanghae99lv3.entity.Lecture;
import org.sparta.hanghae99lv3.entity.Teacher;
import org.sparta.hanghae99lv3.message.ErrorMessage;
import org.sparta.hanghae99lv3.message.SuccessMessage;
import org.sparta.hanghae99lv3.repository.LectureRepository;
import org.sparta.hanghae99lv3.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final TeacherRepository teacherRepository;

    public LectureResponseDto createLecture(LectureRequestDto lectureRequestDto) {
        Long teacherId = lectureRequestDto.getTeacherId();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.EXIST_TEACHER_ERROR_MESSAGE.getErrorMessage())
        );

        Lecture lecture = new Lecture(lectureRequestDto, teacher);
        Lecture saveLecture = lectureRepository.save(lecture);

        return new LectureResponseDto(saveLecture);
    }

    public LectureResponseDto getLecture(Long lectureId) {
        return new LectureResponseDto(findLecture(lectureId));
    }

    public List<LectureResponseDto> getLectureListForTeacher(Long teacherId) {
        return lectureRepository.findAllByTeacherIdOrderByRegiDateDesc(teacherId)
                .stream().map(LectureResponseDto::new).toList();
    }
    public List<LectureResponseDto> getLectureListForCategory(String category) {
        return lectureRepository.findAllByCategoryOrderByRegiDateDesc(category)
                .stream().map(LectureResponseDto::new).toList();
    }

    private Lecture findLecture(Long lectureId){
        return lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.EXIST_LECTURE_ERROR_MESSAGE.getErrorMessage()));
    }

    @Transactional
    public LectureResponseDto updateLecture(Long lectureId, LectureRequestDto lectureRequestDto) {
        Lecture lecture = findLecture(lectureId);
        lecture.update(lectureRequestDto);

        return new LectureResponseDto(lecture);
    }

    public ResponseEntity<String> deleteLecture(Long lectureId) {
        Lecture lecture = findLecture(lectureId);
        lectureRepository.delete(lecture);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessMessage.DELETE_SUCCESS_MESSAGE.getSuccessMessage());
    }
}
