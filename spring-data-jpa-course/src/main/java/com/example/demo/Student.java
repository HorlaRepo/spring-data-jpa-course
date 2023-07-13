package com.example.demo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "email"
                )
        }
)
@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class Student {

    public Student(String firstName,
                   String lastName,
                   String email,
                   Integer age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }


    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    @Column(name = "id",updatable = false)

    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )

    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )

    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Book> books = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student"
    )


    private List<Enrollment> enrollments = new ArrayList<>();

    public void addBook(Book book){
        if(!this.books.contains(book)){
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book){
        if(this.books.contains(book)){
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }


    public void addEnrollment(Enrollment enrollment){
        if(!enrollments.contains(enrollment)){
            enrollments.add(enrollment);
        }
    }

    public void removeEnrollment(Enrollment enrollment){
        enrollments.remove(enrollment);
    }

}
