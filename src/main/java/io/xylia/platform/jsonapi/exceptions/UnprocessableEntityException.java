package io.xylia.platform.jsonapi.exceptions;

import lombok.Getter;

/**
 * A generic exception for an unprocessable entity.
 * 
 *
 */
@Getter
public class UnprocessableEntityException extends RuntimeException {

  private static final long serialVersionUID = 4263597376937376571L;

  private final String field;

  public UnprocessableEntityException(String message) {
    this("", message);
  }

  public UnprocessableEntityException(String field, String message) {
    super(message);
    this.field = field;
  }

}
