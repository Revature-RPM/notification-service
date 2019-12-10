package com.revature.rpm.exceptions;

import java.io.IOException;

/**
 * In case of attempted subversion around Zuul we want to invalidate the session, so we can
 * guarantee that the user will not be authenticated
 */
public class SubversionAttemptException extends IOException {

  private static final long serialVersionUID = -1736687159994666796L;

  public SubversionAttemptException(String message, Throwable cause) {
    super(message, cause);
  }

  public SubversionAttemptException(String message) {
    super(message);
  }

  public SubversionAttemptException(Throwable cause) {
    super(cause);
  }
}
