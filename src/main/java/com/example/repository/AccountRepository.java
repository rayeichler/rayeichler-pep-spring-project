package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query("select a from Account a where a.username = :username and a.password = :password")
    Optional<Account> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    //Optional<Account> findByUsernameAndPassword(String username, String password);
}
