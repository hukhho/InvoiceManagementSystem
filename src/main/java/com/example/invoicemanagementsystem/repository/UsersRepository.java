package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.Admin;
import com.example.invoicemanagementsystem.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);


}