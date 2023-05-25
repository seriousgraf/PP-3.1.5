package ru.kata.spring.security.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.security.models.Role;
import ru.kata.spring.security.models.User;
import ru.kata.spring.security.repositories.RoleRepository;
import ru.kata.spring.security.repositories.UserRepository;

import java.util.List;
//   InitUsers выполняет инициализацию пользователей и ролей при запуске приложения, если репозиторий пользователей не содержит ни одной записи.

@Component
public class InitUsers implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Role roleAdmin = new Role("ROLE_ADMIN");

            Role roleUser = new Role("ROLE_USER");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);

            User user = new User();
            user.setName("Alex");
            user.setSurname("User");
            user.setAge(23);
            user.setEmail("alexuser@mail.ru");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(List.of(roleUser));
            userRepository.save(user);

            User admin = new User();
            admin.setName("Alex");
            admin.setSurname("Admin");
            admin.setAge(23);
            admin.setEmail("alexadmin@mail.ru");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(List.of(roleAdmin, roleUser));
            userRepository.save(admin);
        }
    }
}
