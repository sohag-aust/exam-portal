package com.exam.demo.services;

import com.exam.demo.model.User;
import com.exam.demo.model.UserRole;

import java.util.Set;

public interface UserService {

    // creating user
    public User createUser(User user, Set<UserRole> userRoles);

}
