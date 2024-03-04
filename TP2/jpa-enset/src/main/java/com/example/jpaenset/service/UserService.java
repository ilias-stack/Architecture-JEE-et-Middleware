package com.example.jpaenset.service;

import com.example.jpaenset.entities.Role;
import com.example.jpaenset.entities.User;

public interface UserService {
    User addNewUser(User u);
    Role addNewRole(Role r);
    User findUserByName(String n);
    Role findRoleByName(String n);
    void addRoleToUser(String userName,String roleName);
}
