/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.example.music_library.models.User;
import com.example.music_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IvanAnemi4niq
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    
     public UserService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
    public boolean registerUser(User user) {
    userRepository.save(user);
    return true;
  }
}
