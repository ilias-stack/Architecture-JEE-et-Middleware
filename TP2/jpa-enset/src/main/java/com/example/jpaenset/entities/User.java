package com.example.jpaenset.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    private String id;
    @Column(unique = true,length = 30)
    private String username;
    private String password;
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Role> roles;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }
}
