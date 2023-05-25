package ru.kata.spring.security.dto;

import lombok.*;
import ru.kata.spring.security.models.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
// класс UserDTO определяет структуру данных для представления информации о пользователе
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {


    private int id;

    @Size(min = 1, max = 15, message = "Имя должно содержать от 1 до 15 символов.")
    @NotBlank(message = "Это поле не должно быть пустым")
    private String name;


    private String surname;

    private int age;

    @Email(message = "Неверный формат для этого поля")
    @NotBlank(message = "Это поле не должно быть пустым")
    private String email;

    @NotBlank(message = "Это поле не должно быть пустым")
    private String password;

    private Collection<Role> roles;
}
