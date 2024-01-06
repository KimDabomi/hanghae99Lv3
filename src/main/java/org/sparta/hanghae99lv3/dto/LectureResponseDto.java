package org.sparta.hanghae99lv3.dto;

import lombok.Getter;
import org.sparta.hanghae99lv3.entity.Lecture;
import org.sparta.hanghae99lv3.entity.Teacher;

import java.util.Date;

@Getter
public class LectureResponseDto {
    private Teacher teacher;
    private String lectureName;
    private Integer price;
    private String lectureIntro;
    private String category;
    private Date regiDate;

    public LectureResponseDto(Lecture saveLecture) {
        this.teacher = saveLecture.getTeacher();
        this.lectureName = saveLecture.getLectureName();
        this.price = saveLecture.getPrice();
        this.lectureIntro = saveLecture.getLectureIntro();
        this.category = saveLecture.getCategory();
        this.regiDate = saveLecture.getRegiDate();
    }
}
