package com.github.pingpongapi.domains.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pingpongapi.domains.matches.Matches;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @JsonIgnore
  private String salt;

  private int wins;

  private int losses;

  private int totalScore;

  @OneToMany
  private List<Matches> matches;

  public Users() {
  }

  public Users(@NotBlank String name,
      @NotBlank String username, @NotBlank String password, String salt, int wins, int losses, int totalScore) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.salt = salt;
    this.wins = wins;
    this.losses = losses;
    this.totalScore = totalScore;
  }

  public Users(@NotBlank String name,
      @NotBlank String username, @NotBlank String password) {
    this.name = name;
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public int getLosses() {
    return losses;
  }

  public void setLosses(int losses) {
    this.losses = losses;
  }

  public int getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }

  public void addWin() {
    this.wins += 1;
  }

  public void addLoss() {
    this.losses += 1;
  }

  public void addScore(int score) {
    this.totalScore += score;
  }

  @Override
  public String toString() {
    return "Users{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", wins=" + wins +
        ", losses=" + losses +
        '}';
  }
}
