package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.models.UserEntity;

@Component
public class UserMapper {

    public UserEntity toUserEntity(Register register) {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(register.getFirstName());
        userEntity.setLastName(register.getLastName());
        userEntity.setEmail(register.getUsername());
        userEntity.setRole(register.getRole());
        userEntity.setPhone(register.getPhone());
        userEntity.setImage("Нет фотографии");

        return userEntity;
    }
}
