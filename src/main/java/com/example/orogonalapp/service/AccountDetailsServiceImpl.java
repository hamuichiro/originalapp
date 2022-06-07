package com.example.orogonalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.originalapp.entity.Account;
import com.example.originalapp.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService { //UserRepositoryの結果の取得
	//@Autowiredは別のクラスを使えるようにしてくれるもの,@Autowiredを使わないといちいちnewを書いてクラスを呼び出さないといけない
    @Autowired
    private AccountRepository repository;
    
    protected static Logger log = LoggerFactory.getLogger(AccountDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	log.debug("username={}", username);

        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        Account entity = repository.findByUsername(username);

        return entity;
    }

}