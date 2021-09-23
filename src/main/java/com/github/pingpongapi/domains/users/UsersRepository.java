package com.github.pingpongapi.domains.users;

import java.util.List;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
  boolean existsUserByUsername(String username);

  Users findByUsername(String username);
}
