package com.elecon.asset_mgt.Exceptions;

public class ForeignKeyViolationException extends RuntimeException {
  public ForeignKeyViolationException(String message) {
    super(message);
  }
}
