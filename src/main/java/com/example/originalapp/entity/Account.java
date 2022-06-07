package com.example.originalapp.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


@Entity
@Table(name = "account")
@Data
public class Account implements UserDetails, AccountInf {
	private static final long serialVersionUID = 1L;
	
    public Account() {
        super();
    }
	
    public Account(String name, String password, Authority authority) {
    	this.username = name;
        this.password = password;
        this.authority = authority;
    }
	
	@Id //識別子(主キー)であることを指定
    @SequenceGenerator(name = "users_id_seq") //シーケンスを使った識別子の自動生成
    @GeneratedValue(strategy = GenerationType.IDENTITY) //データベースのIDENTITY自動生成機能を利用して，識別子を自動生成
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;
    
	
    public enum Authority {
        ROLE_USER, ROLE_ADMIN
    };
    

  

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //ユーザーに付与された権限を返す
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority.toString()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { //ユーザーのアカウントの有効期限が切れているかどうかを示す
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //ユーザーがロックされているかロック解除されているかを示す
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //ユーザーの資格情報（パスワード）の有効期限が切れているかどうかを示す
        return true;
    }

    @Override
    public boolean isEnabled() { //ユーザーが有効か無効かを示す
        return true;
    }

}