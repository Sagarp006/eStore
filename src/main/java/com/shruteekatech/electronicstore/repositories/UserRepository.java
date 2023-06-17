package com.shruteekatech.electronicstore.repositories;

import com.shruteekatech.electronicstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String emailId);

  //  Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keyword);
}
