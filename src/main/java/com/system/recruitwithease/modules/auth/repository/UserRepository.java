package com.system.recruitwithease.modules.auth.repository;

import com.system.recruitwithease.modules.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);

    Optional<User> findUserByUserName(String email);
}
