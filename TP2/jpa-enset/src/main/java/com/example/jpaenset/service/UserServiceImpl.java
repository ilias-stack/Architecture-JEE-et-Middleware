package com.example.jpaenset.service;

import com.example.jpaenset.entities.Role;
import com.example.jpaenset.entities.User;
import com.example.jpaenset.repositories.RoleRepository;
import com.example.jpaenset.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User addNewUser(User u) {
        u.setId(UUID.randomUUID().toString());
        return userRepository.save(u);
    }

    @Override
    public Role addNewRole(Role r) {
        return roleRepository.save(r);
    }

    @Override
    public User findUserByName(String n) {
        return userRepository.findUserByUsername(n);
    }

    @Override
    public Role findRoleByName(String n) {
        return roleRepository.findRoleByRoleName(n);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User u = userRepository.findUserByUsername(userName);
        Role r = roleRepository.findRoleByRoleName(roleName);
        if(u.getRoles()!=null) {
            u.getRoles().add(r);
            r.getUsers().add(u);
        }

    }
}
