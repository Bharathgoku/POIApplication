package com.poi.exceptions;

public class InvalidCityException extends RuntimeException{

  public InvalidCityException() {
    super();
  }

  public InvalidCityException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }

  public InvalidCityException(String errorMessage) {
    super(errorMessage);
  }

  public InvalidCityException(Throwable cause) {
    super(cause);
  }

}
