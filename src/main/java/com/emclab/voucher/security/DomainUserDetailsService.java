package com.emclab.voucher.security;

import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.domain.User;
import com.emclab.voucher.repository.MyUserRepository;
import com.emclab.voucher.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final MyUserRepository userRepository;

    public DomainUserDetailsService(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        //        if (new EmailValidator().isValid(login, null)) {
        //            return userRepository
        //                .findOneWithAuthoritiesByEmailIgnoreCase(login)
        //                .map(user -> createSpringSecurityUser(login, user))
        //                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        //        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findByUsername(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, MyUser user) {
        //        if (!user.isActivated()) {
        //            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        //        }
        List<GrantedAuthority> grantedAuthorities = user
            .getRoles()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
