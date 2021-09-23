package com.github.pingpongapi.exceptions;

public class CannotDeleteMatch extends RuntimeException{
  public CannotDeleteMatch() {
  }
  
  public CannotDeleteMatch(String message) { super(message); }
}
