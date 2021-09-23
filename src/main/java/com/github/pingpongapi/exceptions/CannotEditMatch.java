package com.github.pingpongapi.exceptions;

public class CannotEditMatch extends RuntimeException{
  public CannotEditMatch() {
  }

  public CannotEditMatch(String message) { super(message); }
}
