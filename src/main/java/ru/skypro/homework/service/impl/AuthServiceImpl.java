package ru.skypro.homework.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public boolean login(String userName, String password) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(userName);

        if (userEntity.isEmpty()) {
            return false;
        }

        return userEntity.filter(entity -> encoder.matches(password, entity.getPassword())).isPresent();
    }


    @Override
    public boolean register(Register register) {
        if (register == null) {
            return false;
        }

        if (userRepository.existsByEmail(register.getUsername())) {
            return false;
        }

        UserEntity user = userMapper.toUserEntity(register);

        user.setPassword(encoder.encode(register.getPassword()));

        userRepository.save(user);

        return true;
    }
}
