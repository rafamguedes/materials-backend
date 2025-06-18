package com.materials.api.repository;

import com.materials.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
  boolean existsByEmail(String email);

  Optional<User> findByRegistry(String registry);

  Optional<User> findByEmail(String email);
}
