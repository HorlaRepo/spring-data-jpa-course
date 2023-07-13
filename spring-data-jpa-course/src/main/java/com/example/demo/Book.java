package com.example.demo;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Book")
@Table(name = "book")

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )

    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "book_name",
            nullable = false
    )
    private String bookName;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Timestamp createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_book_fk")
    )
    private Student student;

    public Book(String bookName, Timestamp createdAt){
        this.bookName = bookName;
        this.createdAt = createdAt;
    }
}
