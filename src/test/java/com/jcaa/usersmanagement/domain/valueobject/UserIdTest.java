package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Value Object: UserId - Pruebas de validación y normalización")
class UserIdTest {

  @DisplayName("Debería crear un UserId con el valor normalizado (trimmed)")
  @ParameterizedTest
  @ValueSource(strings = {" user123 ", "  user123  ", "user123\t"})
  void shouldCreateUserIdWithTrimmedValue(String input) {
    // Arrange
    final String correctUserId = "user123";

    // Act
    final UserId userId = new UserId(input);

    // Assert
    assertEquals(correctUserId, userId.toString());
  }

  @Test
  @DisplayName("Debería lanzar NullPointerException cuando el ID es nulo")
  void shouldThrowNullPointerExceptionWhenUserIdIsNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserId(null));
  }

  @DisplayName("Debería lanzar InvalidUserIdException cuando el ID es vacío o solo espacios")
  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  void shouldThrowIllegalArgumentExceptionWhenUserIdIsEmpty(String input) {
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> new UserId(input));
  }
}
