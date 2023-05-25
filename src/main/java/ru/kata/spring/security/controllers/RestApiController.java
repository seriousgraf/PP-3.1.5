package ru.kata.spring.security.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.security.dto.RoleDTO;
import ru.kata.spring.security.dto.UserDTO;
import ru.kata.spring.security.models.Role;
import ru.kata.spring.security.models.User;
import ru.kata.spring.security.security.UserUserDetailsImpl;
import ru.kata.spring.security.services.RoleService;
import ru.kata.spring.security.services.UserService;
import ru.kata.spring.security.util.UserNotCreatedException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestApiController(UserService userService, RoleService roleService) {
        this.userService = userService;

        this.roleService = roleService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> showAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/info")
    public UserDTO showUser(Principal principal) {
        UserUserDetailsImpl userDetails = (UserUserDetailsImpl) ((Authentication) principal).getPrincipal();
        User user = userService.getUser(userDetails.getId());
        return convertToUserDTO(user);
    }



  @GetMapping("/roles")
  public ResponseEntity<List<RoleDTO>> getRoles() {
      return  ResponseEntity.ok().body(roleService.findAllRoles().stream().map(this::convertToRoleDTO)
              .collect(Collectors.toList()));
  }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addNewUser(@Valid @RequestBody User user,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
             StringBuilder errorMsg = new StringBuilder();

             List<FieldError> errors = Collections.singletonList(bindingResult.getFieldError());
             for(FieldError error : errors) {
                 errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                         .append(";");
             }
             throw new UserNotCreatedException(errorMsg.toString());
        }

        userService.saveUser(user);
        UserDTO userDTO = convertToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/users")

    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody User user,
                                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = Collections.singletonList(bindingResult.getFieldError());
            for(FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }

        userService.updateUser(user);
        UserDTO userDTO = convertToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "Пользователь с id = " + id + " удален";
    }



    private User convertToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    private RoleDTO convertToRoleDTO(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(role, RoleDTO.class);
    }
}
