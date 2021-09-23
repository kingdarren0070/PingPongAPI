package com.github.pingpongapi.domains.users;

import com.github.pingpongapi.encryption.Hash;
import com.github.pingpongapi.encryption.Salt;
import com.github.pingpongapi.exceptions.FieldAlreadyPresent;
import com.github.pingpongapi.exceptions.ResourceNotFound;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService{
  private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

  @Autowired
  private UsersRepository usersRepository;

  /**
   * Gets all users
   * @return - list of all users
   */
  @Override
  public List<Users> getAll() {
    List<Users> usersList = new ArrayList<>();

    try {
      usersList = usersRepository.findAll();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return usersList;
  }

  /**
   * Gets specific user by ID
   * @param id - id of user
   * @return user with given ID
   */
  @Override
  public Users getById(Long id) {
    Optional<Users> user = Optional.ofNullable(null);

    try {
      user = usersRepository.findById(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    if (user.isEmpty()) {
      throw new ResourceNotFound();
    } else {
      return user.get();
    }
  }

  /**
   * Gets all users sorted in descending order of total score (leaderboard)
   * @return list of all users sorted in descending order of total score
   */
  @Override
  public List<Users> getLeaderboardByTotalScore() {
    List<Users> leaderboard = new ArrayList<>();

    try {
      leaderboard = usersRepository.findAll(Sort.by(Sort.Direction.DESC, "totalScore"));
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return leaderboard;
  }

  /**
   * Creates a new user
   * @param user - new user to be created
   * @return newly created user
   * @throws NoSuchAlgorithmException
   */
  @Override
  public Users createUser(Users user) throws NoSuchAlgorithmException {
    if(usersRepository.existsUserByUsername(user.getUsername())) {
      throw new FieldAlreadyPresent("Email already in use");
    }

    String password = user.getPassword();
    String salt = String.valueOf(Salt.getSaltCode());
    user.setSalt(salt);
    password += salt;
    password = Hash.getHex(password);
    user.setPassword(password);

    Users postedUser = null;

    try {
      postedUser = usersRepository.save(user);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return postedUser;
  }

  /**
   * Updates a new user
   * @param id - id of user to update
   * @param user - updated user
   * @return updated user
   * @throws NoSuchAlgorithmException
   */
  @Override
  public Users updateUser(Long id, Users user) throws NoSuchAlgorithmException {
    Users updatedUser = null;

    try {
      Optional<Users> userToUpdate = usersRepository.findById(id);
      if(userToUpdate.isEmpty()) {
        throw new ResourceNotFound();
      }
      if (userToUpdate.get().getPassword() != user.getPassword()){
        String password = user.getPassword();
        String salt = String.valueOf(Salt.getSaltCode());
        user.setSalt(salt);
        password += salt;
        password = Hash.getHex(password);
        user.setPassword(password);
      }
      updatedUser = usersRepository.save(user);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }

    return updatedUser;
  }

  /**
   * Deletes a user
   * @param id - id of user to get deleted
   */
  @Override
  public void deleteUser(Long id) {
    try {
      usersRepository.deleteById(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Gets a user by username
   * @param username - username of user
   * @return user with given username
   */
  @Override
  public Users getUserByUsername(String username) {
    Users result;
    try {
      result = usersRepository.findByUsername(username);
    } catch (Exception e) {
      throw new ResourceNotFound("Could not locate a user with the email: " + username);
    }

    return result;
  }
}
