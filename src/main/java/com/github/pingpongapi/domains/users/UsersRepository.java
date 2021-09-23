package com.github.pingpongapi.domains.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
  boolean existsUserByUsername(String username);

  Users findByUsername(String username);
}
