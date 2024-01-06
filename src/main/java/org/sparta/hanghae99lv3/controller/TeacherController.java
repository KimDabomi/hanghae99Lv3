package org.sparta.hanghae99lv3.controller;

import org.sparta.hanghae99lv3.dto.TeacherRequestDto;
import org.sparta.hanghae99lv3.dto.TeacherResponseDto;
import org.sparta.hanghae99lv3.entity.Teacher;
import org.sparta.hanghae99lv3.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teachers")
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto requestDto) {
        return teacherService.createTeacher(requestDto);
    }

    @GetMapping("/teachers/{teacherId}")
    public Teacher getTeacher(@PathVariable Long teacherId, @RequestBody TeacherRequestDto requestDto) {
        return teacherService.getTeacher(teacherId, requestDto);
    }

    @PutMapping("/teachers/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherRequestDto requestDto) {
        return teacherService.updateTeacher(teacherId, requestDto);
    }

    @DeleteMapping("/teachers/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }
}
