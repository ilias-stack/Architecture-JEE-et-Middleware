package com.example.jpaenset.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(unique = true , length = 30)
    private String roleName;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
//    @JoinTable(name = "USERS_ROLES")
    @ToString.Exclude
    private List<User> users;

    public Role(Long id, String desc, String roleName) {
        this.id = id;
        this.description = desc;
        this.roleName = roleName;
        users = new ArrayList<>();
    }
}
