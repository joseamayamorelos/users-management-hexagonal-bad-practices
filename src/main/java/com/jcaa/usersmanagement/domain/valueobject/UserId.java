package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUserIdException;
import java.util.Objects;

public record UserId(String value) {

  public UserId {
    Objects.requireNonNull(value, "UserId cannot be null");
    final String trimmedValue = value.trim();
    validateNotEmpty(trimmedValue);
    value = trimmedValue;
  }

  private static void validateNotEmpty(final String value) {
    if (value.isEmpty()) {
      throw InvalidUserIdException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
