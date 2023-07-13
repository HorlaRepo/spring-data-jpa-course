package com.example.demo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")

@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class Course {

    public Course(String name, String department){
        this.name = name;
        this.department = department;
    }


    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )

    @Column(name = "id",updatable = false)

    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "department",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String department;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    public void addEnrollment(Enrollment enrollment){
        if(!enrollments.contains(enrollment)){
            enrollments.add(enrollment);
        }
    }

    public void removeEnrollment(Enrollment enrollment){
        enrollments.remove(enrollment);
    }

}
