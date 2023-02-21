//package com.mendes.library.config.security.user;
//
//
//import com.mendes.library.model.Authority;
//import com.mendes.library.model.Role;
//import com.mendes.library.repository.AuthorityRepository;
//import com.mendes.library.repository.RoleRepository;
//import com.mendes.library.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.Set;
//
////@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    boolean alrearySetup = false;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private AuthorityRepository authorityRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (alrearySetup)
//            return;
//
//        Authority readAuthority = createAuthorityIfNotFound("READ_AUTHORITY");
//        Authority writeAuthority = createAuthorityIfNotFound("WRITE_AUTHORITY");
//
//        Set<Authority> adminAuthority = (Set<Authority>) Arrays.asList(readAuthority, writeAuthority);
//        createRoleIfNotFound("ROLE_ADMIN", adminAuthority);
//        createRoleIfNotFound("ROLE_USER", (Set<Authority>) Arrays.asList(readAuthority));
//
//
//    }
//
//    @Transactional
//    Authority createAuthorityIfNotFound(String name) {
//        Authority authority = authorityRepository.findByName(name);
//        if (authority == null) {
//            authority = new Authority(name);
//            authorityRepository.save(authority);
//        }
//
//        return authority;
//    }
//
//    @Transactional
//    Role createRoleIfNotFound(String name, Set<Authority> authorities) {
//        Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            role.setAuthorities(authorities);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//}
//
