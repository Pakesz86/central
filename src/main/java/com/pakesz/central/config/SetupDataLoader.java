package com.pakesz.central.config;


import com.pakesz.central.entity.ERole;
import com.pakesz.central.entity.Role;
import com.pakesz.central.entity.User;
import com.pakesz.central.repository.RoleRepository;
import com.pakesz.central.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        var adminRole = createRoleIfNotFound(ERole.ADMIN.getNameWithPrefix());

        var user = userRepository.findByEmail("test@test.com");
        if (user.isEmpty()) {
            userRepository.save(new User()
                    .setName("Test")
                    .setEmail("test@test.com")
                    .setPassword(passwordEncoder.encode("test"))
                    .setRoles(Collections.singletonList(adminRole)));
        }

        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role().setName(name);
            roleRepository.save(role);
        }
        return role;
    }
}
