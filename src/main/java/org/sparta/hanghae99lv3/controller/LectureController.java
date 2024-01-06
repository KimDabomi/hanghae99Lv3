package org.sparta.hanghae99lv3.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.dto.LectureRequestDto;
import org.sparta.hanghae99lv3.dto.LectureResponseDto;
import org.sparta.hanghae99lv3.message.ErrorMessage;
import org.sparta.hanghae99lv3.service.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("lecture")
    public ResponseEntity<LectureResponseDto> createLecture(@RequestBody LectureRequestDto lectureRequestDto){
        LectureResponseDto lectureResponseDto = lectureService.createLecture(lectureRequestDto);
        return ResponseEntity.ok(lectureResponseDto);
    }

    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<LectureResponseDto> getLecture(@PathVariable Long lectureId){
        LectureResponseDto lectureResponseDto = lectureService.getLecture(lectureId);
        return ResponseEntity.ok(lectureResponseDto);
    }

    @GetMapping("/lecture/teacher/{teacherId}")
    public ResponseEntity<List<LectureResponseDto>> getLectureListForTeacher(@PathVariable Long teacherId){
        List<LectureResponseDto> lectureResponseDtoList = lectureService.getLectureListForTeacher(teacherId);
        return ResponseEntity.ok(lectureResponseDtoList);
    }

    @GetMapping("/lecture")
    public ResponseEntity<List<LectureResponseDto>> getLectureListForCategory(@RequestParam String category){
        List<LectureResponseDto> lectureResponseDtoList = lectureService.getLectureListForCategory(category);
        return ResponseEntity.ok(lectureResponseDtoList);
    }

    @PutMapping("/lecture/{lectureId}")
    public ResponseEntity<LectureResponseDto> updateLecture(@PathVariable Long lectureId, @RequestBody LectureRequestDto lectureRequestDto){
        LectureResponseDto lectureResponseDto = lectureService.updateLecture(lectureId, lectureRequestDto);
        return ResponseEntity.ok(lectureResponseDto);
    }

    @DeleteMapping("/lecture/{lectureId}")
    public ResponseEntity<Long> deleteLecture(@PathVariable Long lectureId){
        Long id = lectureService.deleteLecture(lectureId);
        return ResponseEntity.ok(id);
    }
}
