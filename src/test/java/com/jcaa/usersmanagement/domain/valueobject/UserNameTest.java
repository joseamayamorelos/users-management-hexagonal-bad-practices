package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Value Object: UserName - Pruebas de validación y normalización")
class UserNameTest {

  @DisplayName("Debería validar y normalizar el nombre de usuario cuando tiene la longitud mínima")
  @ParameterizedTest
  @ValueSource(strings = {"John Arrieta", "   John Arrieta   ", "John Arrieta \t"})
  void shouldValidateUserNameMinimumLength(final String userName) {
    // Arrange
    final String correctUserName = "John Arrieta";

    // Act
    final UserName userNameVo = new UserName(userName);

    // Assert
    assertEquals(correctUserName, userNameVo.toString());
  }

  // -- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Debería lanzar NullPointerException cuando el nombre es nulo")
  void shouldValidateUserNameIsNotNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserName(null));
  }

  @DisplayName("Debería fallar cuando el nombre es vacío o no cumple con la longitud mínima")
  @ParameterizedTest
  @ValueSource(
      strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Ty  ", "", "   Cy ", "Ed\t"})
  void shouldValidateUserNameIsNotEmptyAndMinimumLength(final String userName) {
    // Act & Assert
    assertThrows(InvalidUserNameException.class, () -> new UserName(userName));
  }
}
