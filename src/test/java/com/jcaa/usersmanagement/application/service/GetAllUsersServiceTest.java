package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllUsersPort;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Pruebas para GetAllUsersService cubriendo el retorno de lista completa y manejo de lista vacía.
 */
@DisplayName("Application Service: GetAllUsersService")
@ExtendWith(MockitoExtension.class)
class GetAllUsersServiceTest {

  @Mock private GetAllUsersPort getAllUsersPort;

  private GetAllUsersService service;

  @BeforeEach
  void setUp() {
    service = new GetAllUsersService(getAllUsersPort);
  }

  @Test
  @DisplayName("execute() retorna la lista de usuarios obtenida del puerto")
  void shouldReturnUsersFromPort() {
    // Arrange
    final UserModel user =
        new UserModel(
            new UserId("u-001"),
            new UserName("John Arrieta"),
            new UserEmail("john@example.com"),
            UserPassword.fromHash("$2a$12$abcdefghijklmnopqrstuO"),
            UserRole.ADMIN,
            UserStatus.ACTIVE);
    when(getAllUsersPort.getAll()).thenReturn(List.of(user));

    // Act
    final List<UserModel> result = service.execute();

    // Assert
    assertNotNull(result, "La lista de resultados no debería ser nula");
    assertEquals(1, result.size(), "La lista debería contener exactamente un usuario");
    assertSame(user, result.get(0), "El usuario en la lista debería ser la instancia mockeada");
  }

  @Test
  @DisplayName("Debería retornar una lista vacía cuando no hay usuarios registrados")
  void shouldReturnNullWhenNoUsers() {
    // Arrange
    when(getAllUsersPort.getAll()).thenReturn(List.of());

    // Act
    final List<UserModel> result = service.execute();

    // Assert
    assertNotNull(result, "La lista de resultados debería estar vacía, no ser nula");
    assertTrue(result.isEmpty(), "La lista debería estar vacía");
  }
}
