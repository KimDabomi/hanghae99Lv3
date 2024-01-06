package org.sparta.hanghae99lv3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.hanghae99lv3.dto.LectureRequestDto;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lecture")
public class Lecture extends com.example.lv02.entity.Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @ManyToOne
    @JoinColumn(name = "teacher_id",nullable = false)
    private Teacher teacher;

    @Column(name = "lecture_name", nullable = false)
    private String lectureName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "lecture_intro", nullable = false)
    private String lectureIntro;

    @Column(name = "category", nullable = false)
    private String category;

    public Lecture(LectureRequestDto lectureRequestDto, Teacher teacher) {
        this.teacher = teacher;
        this.lectureName = lectureRequestDto.getLectureName();
        this.price = lectureRequestDto.getPrice();
        this.lectureIntro = lectureRequestDto.getLectureIntro();
        this.category = lectureRequestDto.getCategory();
    }

    public void update(LectureRequestDto lectureRequestDto){
        this.teacher = lectureRequestDto.getTeacher();
        this.lectureName = lectureRequestDto.getLectureName();
        this.price = lectureRequestDto.getPrice();
        this.lectureIntro = lectureRequestDto.getLectureIntro();
        this.category = lectureRequestDto.getCategory();
    }
}
