package com.example.demo;

import com.github.javafaker.Faker;
import net.bytebuddy.asm.Advice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository cardRepository
                                        ){
        return args -> {
            Faker faker = new Faker();
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = String.format("%s.%s@francis.com",firstName,lastName);
                Student student = new Student(firstName
                        ,lastName,email, faker.number().numberBetween(17,55));
            StudentIdCard idCard = new StudentIdCard(
                    "1234567890",
                    student);




            student.addBook(
                    new Book("Clean Code",Timestamp.from(Instant.now())));
            student.addBook(
                    new Book("Spring Data JPA",Timestamp.from(Instant.now())));
            student.addBook(
                    new Book("Heroes",Timestamp.from(Instant.now())));

            student.setStudentIdCard(idCard);

            student.addEnrollment(new Enrollment(
                    new EnrollmentId(1L,1L),
                    student, new Course("Cybersecurity","IT"),
                    LocalDateTime.now()
            ));

            student.addEnrollment(new Enrollment(
                    new EnrollmentId(1L,2L),
                    student, new Course("Algorithms","IT"),
                    LocalDateTime.now().minusDays(18)
            ));

            studentRepository.save(student);


            studentRepository.findById(1L).ifPresent(
                    s -> {
                        System.out.println("fetch books lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(
                                    s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    }
            );
            //cardRepository.findById(1L)
                //    .ifPresent(System.out::println);

            //studentRepository.deleteById(1L);


        };


    }

    private void generateRandomStudents(StudentRepository studentRepository){
        Faker faker = new Faker();
        for (int i = 0; i <20 ; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@francis.com",firstName,lastName);
            Student student = new Student(firstName
                    ,lastName,email, faker.number().numberBetween(17,55) );
            studentRepository.save(student);
        }

    }

    private void sorting(StudentRepository studentRepository){
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void paging(StudentRepository studentRepository){
        PageRequest pageRequest = PageRequest.of(
                0,
                5,
                Sort.by("firstName").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

}
