package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Value("${avatars.users.dir}")
    private String avatarsDir;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileService fileService;

    public User getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        ;
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
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + name));

        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), userEntity.getPassword())) {
            return false;
        }

        userEntity.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(userEntity);
        return true;
    }


    public void updateImage(MultipartFile image) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + name));
        String path = userEntity + "." + getExtensions(Objects.requireNonNull(image.getOriginalFilename()));

        String filePath = fileService.uploadFile(avatarsDir, path, image);
        userEntity.setImage(filePath);
        userRepository.save(userEntity);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
