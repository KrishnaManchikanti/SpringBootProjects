package com.Assignment.LibraryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String phNo;
    @Size(min = 5, max = 30)
    private String address;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();
}