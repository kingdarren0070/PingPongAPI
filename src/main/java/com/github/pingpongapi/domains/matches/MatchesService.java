package com.github.pingpongapi.domains.matches;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MatchesService {
  List<Matches> getAll();

  Matches getById(Long id);

  List<Matches> getAllMatchesByDate(String date);

  Matches createMatch(Matches match);

  Matches updateMatch(Long id, Matches match) throws NoSuchAlgorithmException;

  void deleteMatch(Long id);
}
