package com.example.bookstore.Service;

import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.ServiceInterface.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updateUser) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            user.setAddress(updateUser.getAddress());
            user.setEmail(updateUser.getEmail());
            if(updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            }
            user.setUsername(updateUser.getUsername());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
