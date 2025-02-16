package com.codeit.side.common.adapter.out.security;

import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserQueryRepository userQueryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userQueryRepository.getByEmail(email);
        return new CustomUserDetails(userEntity);
    }
}
