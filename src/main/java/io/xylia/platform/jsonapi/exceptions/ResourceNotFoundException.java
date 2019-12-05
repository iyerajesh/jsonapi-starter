package io.xylia.platform.jsonapi.exceptions;

import lombok.NoArgsConstructor;

/**
 * A generic exception for a resource not found.
 * 
 *
 */
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -7732588675951984494L;

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
