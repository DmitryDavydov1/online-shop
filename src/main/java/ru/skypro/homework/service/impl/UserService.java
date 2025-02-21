package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User getCurrentUser() {
        String name = "alim";
        UserEntity userEntity = userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + name));
        System.out.println(userEntity);
        return userMapper.toUser(userEntity);
    }

    public UpdateUser updateUser(UpdateUser updateUser) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + name));

        userEntity.setFirstName(updateUser.getFirstName());
        userEntity.setLastName(updateUser.getLastName());
        userEntity.setPhone(updateUser.getPhone());

        userRepository.save(userEntity);
        return updateUser;

    }

    public boolean updatePassword(NewPassword newPassword) {
        String name = "string5";
        UserEntity userEntity = userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + name));

        if (userEntity.getPassword().equals(newPassword.getCurrentPassword())) {
            userEntity.setPassword(newPassword.getNewPassword());
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }


}
