package com.github.pingpongapi.exceptions;

public class FieldAlreadyPresent extends RuntimeException{
  public FieldAlreadyPresent() {
  }

  public FieldAlreadyPresent(String message) {
    super(message);
  }
}
