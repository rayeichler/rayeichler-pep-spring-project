package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    Optional<Account> findByUsernameAndPassword(String username, String password);

    // @Query("FROM Account WHERE posted_by = :posted_by")
    // Account verifyUser(Integer posted_by);
}
