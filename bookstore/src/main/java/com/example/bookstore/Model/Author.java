package com.example.bookstore.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"books"})
@ToString(exclude = {"books"})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)

    private Set<Book> books = new HashSet<>();

    public Author(Long id, String firstName, String lastName, LocalDate birthDate){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
