package co.edu.usco.pw.loginSpringSecurityBasic.service;


import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.RoleEntity;
import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.UserEntity;
import co.edu.usco.pw.loginSpringSecurityBasic.persistence.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        for (RoleEntity role : user.getRoles()){
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())));
        }

        return new User(user.getUsername(),
                user.getPassword(),
                authorityList);
    }
}
