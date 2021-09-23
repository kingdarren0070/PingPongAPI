package com.github.pingpongapi.domains.matches;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Long> {
  List<Matches> findMatchesByDate(String date);
}
