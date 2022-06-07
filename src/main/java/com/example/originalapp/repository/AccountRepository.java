package com.example.originalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.originalapp.entity.Account;

@Repository //インターフェイスがJpaRepositoryであることを示す
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByUsername(String username); //usernameを条件として検索し、レコードを取得
}