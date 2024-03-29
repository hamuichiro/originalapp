package com.example.originalapp.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.originalapp.entity.Account;
import com.example.originalapp.repository.AccountRepository;

@Configuration
public class FormAuthenticationProvider implements AuthenticationProvider {

    protected static Logger log = LoggerFactory.getLogger(FormAuthenticationProvider.class);

    @Autowired
    private AccountRepository repository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        log.debug("name={}", name);
        log.debug("password={}", password);

        if ("".equals(name) || "".equals(password)) {
            System.out.println(name);
            throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");

        }

        Account entity = repository.findByUsername(name);

        if (entity == null) {
            throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
  
        }

        return new UsernamePasswordAuthenticationToken(entity, password, entity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}