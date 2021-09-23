package com.github.pingpongapi.domains.matches;

import com.github.pingpongapi.domains.users.Users;
import com.github.pingpongapi.domains.users.UsersService;
import com.github.pingpongapi.domains.users.UsersServiceImpl;
import com.github.pingpongapi.exceptions.CannotDeleteMatch;
import com.github.pingpongapi.exceptions.CannotEditMatch;
import com.github.pingpongapi.exceptions.ResourceNotFound;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.crypto.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MatchesServiceImpl implements MatchesService{
  private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

  @Autowired
  private MatchesRepository matchesRepository;

  @Autowired
  private UsersService usersService;

  /**
   * Gets all matches
   * @return all matches
   */
  @Override
  public List<Matches> getAll() {
    List<Matches> matchesList = new ArrayList<>();

    try {
      matchesList = matchesRepository.findAll();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return matchesList;
  }

  /**
   * Gets specific match by ID
   * @param id - Long ID of match
   * @return match with ID
   */
  @Override
  public Matches getById(Long id) {
    Optional<Matches> match = Optional.ofNullable(null);

    try {
      match = matchesRepository.findById(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    if(match.isEmpty()) {
      throw new ResourceNotFound();
    } else {
      return match.get();
    }
  }

  /**
   * Gets all matches by date
   * @param date - String date of match
   * @return - all matches on a certain day
   */
  @Override
  public List<Matches> getAllMatchesByDate(String date) {
    List<Matches> matchesList = new ArrayList<>();

    try {
      matchesList = matchesRepository.findMatchesByDate(date);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return matchesList;
  }

  /**
   * Create a new match
   * @param match - new match
   * @return newly created match
   */
  @Override
  public Matches createMatch(Matches match) {
    Matches postedMatch = null;

    try {
      postedMatch = matchesRepository.save(match);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return postedMatch;
  }

  /**
   * Updates match and updates scores for participants if match is over
   * @param id - id of match
   * @param match - updated match
   * @return updated match
   * @throws NoSuchAlgorithmException
   */
  @Override
  public Matches updateMatch(Long id, Matches match) throws NoSuchAlgorithmException {
    Matches updatedMatch = null;

    try {
      Optional<Matches> matchToUpdate = matchesRepository.findById(id);
      if(matchToUpdate.isEmpty()) {
        throw new ResourceNotFound();
      } else if (matchToUpdate.get().getVictor() != null) {
        throw new CannotEditMatch("Completed Match Cannot Be Edited");
      }
      updatedMatch = matchesRepository.save(match);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    } catch (CannotEditMatch c) {
      logger.error(c.getMessage());
    }

    if(updatedMatch.getVictor() != null) {
      Users participant1 = updatedMatch.getParticipant1();
      Users participant2 = updatedMatch.getParticipant2();
      if(updatedMatch.getVictor() == participant1) {
        participant1.addWin();
        participant2.addLoss();
      } else {
        participant2.addWin();
        participant1.addLoss();
      }
      participant1.addScore(updatedMatch.getScore1());
      participant2.addScore(updatedMatch.getScore2());
      usersService.updateUser(participant1.getId(), participant1);
      usersService.updateUser(participant2.getId(), participant2);
    }

    return updatedMatch;
  }

  /**
   * Deletes a match
   * @param id - id of match to delete
   */
  @Override
  public void deleteMatch(Long id) {
    Matches result;

    try {
      result = getById(id);
      if(result.getVictor() != null) {
        throw new CannotDeleteMatch("Completed Match Cannot Be Deleted");
      }
      matchesRepository.deleteById(id);
      return;
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    } catch (ResourceNotFound r) {
      logger.error(r.getMessage());
    } catch (CannotDeleteMatch x) {
      logger.error(x.getMessage());
    }
  }
}
