package com.latihan.HtmlPdf.service;

import com.latihan.HtmlPdf.model.User;
import com.latihan.HtmlPdf.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public User createUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
