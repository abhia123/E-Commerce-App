package com.bikkadit.electronic.store.repositories;

import com.bikkadit.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);

    List<User> findByNameContaining(String keywords);

}

