package com.example.demo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EnrollmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "course_id")
    private Long courseId;
}
