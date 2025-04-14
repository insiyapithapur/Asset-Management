package com.elecon.asset_mgt.Exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends RuntimeException {

  public IncorrectPasswordException() {
    super("Incorrect password");
  }

  public IncorrectPasswordException(String message) {
    super(message);
  }

  public HttpStatus getStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}
