package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if(userRepository.countByUserId(user.getUserId())>0){
            throw new UserAlreadyExistsException(user.getUserId());
        }
        int userOptional = userRepository.save(user);
        if(userOptional<=0){
            throw new RuntimeException(user.getUserId());
        }


    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) < 1){
            throw new UserNotFoundException(user.getUserId());
        }
        int userOptional = userRepository.update(user);
        if(userOptional<=0){
            throw new RuntimeException("error");
        }
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if(userRepository.countByUserId(userId) < 1){
            throw new UserNotFoundException("user not found");
        }
        int userOptional = userRepository.deleteByUserId(userId);
        if(userOptional <= 0) {
            throw new RuntimeException("error");
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId,userPassword);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(userId);
        }
        int result = userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
        if (result < 1){
            throw new RuntimeException("error");
        }
        return userOptional.orElse(null);
    }

}
