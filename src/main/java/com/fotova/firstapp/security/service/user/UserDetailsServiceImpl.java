package com.fotova.firstapp.security.service.user;

import com.fotova.entity.ClientEntity;
import com.fotova.repository.client.ClientRepositoryImpl;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepositoryImpl userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClientEntity user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Email Not Found : " + email));


        return UserDetailsImpl.build(user);
    }
}
