package com.github.pingpongapi.domains.matches;

import com.github.pingpongapi.domains.users.Users;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Entity table for matches
 */
@Entity
@Table(name = "Matches")
public class Matches {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String matchType;

  @NotNull
  private Users participant1;

  @NotNull
  private Users participant2;

  @NotBlank
  private String date;

  @NotBlank
  private String time;

  private Users victor;

  private int score1;

  private int score2;

  public Matches() {
  }

  public Matches(String matchType, Users participant1,
      Users participant2, String date, String time) {
    this.matchType = matchType;
    this.participant1 = participant1;
    this.participant2 = participant2;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMatchType() {
    return matchType;
  }

  public void setMatchType(String matchType) {
    this.matchType = matchType;
  }

  public Users getParticipant1() {
    return participant1;
  }

  public void setParticipant1(Users participant1) {
    this.participant1 = participant1;
  }

  public Users getParticipant2() {
    return participant2;
  }

  public void setParticipant2(Users participant2) {
    this.participant2 = participant2;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Users getVictor() {
    return victor;
  }

  public void setVictor(Users victor) {
    this.victor = victor;
  }

  public int getScore1() {
    return score1;
  }

  public void setScore1(int score1) {
    this.score1 = score1;
  }

  public int getScore2() {
    return score2;
  }

  public void setScore2(int score2) {
    this.score2 = score2;
  }

  @Override
  public String toString() {
    return "Matches{" +
        "id=" + id +
        ", matchType='" + matchType + '\'' +
        ", participant1=" + participant1 +
        ", participant2=" + participant2 +
        ", date='" + date + '\'' +
        ", time='" + time + '\'' +
        ", victor=" + victor +
        ", score1='" + score1 + '\'' +
        ", score2='" + score2 + '\'' +
        '}';
  }
}
