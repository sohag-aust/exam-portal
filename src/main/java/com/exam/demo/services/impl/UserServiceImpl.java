package com.exam.demo.services.impl;

import com.exam.demo.exceptions.UserNotFoundException;
import com.exam.demo.model.User;
import com.exam.demo.model.UserRole;
import com.exam.demo.repository.RoleRepository;
import com.exam.demo.repository.UserRepository;
import com.exam.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User userFromDb = this.userRepository.findByUsername(user.getUsername());
        try{
            if(userFromDb == null) {
                throw new UserNotFoundException("User not found !");
            }
            Assert.notNull(userFromDb, "User can't be null ..");
            logger.info("User : {}", userFromDb);

            for(UserRole userRole : userRoles) {
                roleRepository.save(userRole.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            this.userRepository.save(user);
        }catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
}
