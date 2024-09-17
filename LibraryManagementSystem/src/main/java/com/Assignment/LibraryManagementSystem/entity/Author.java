package com.Assignment.LibraryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author extends BaseEntity {

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> bookList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Size(min = 3, max = 15)
    String name;
}