package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public final class Main {

  private static final Logger log = Logger.getLogger(Main.class.getName());

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    final DependencyContainer container = buildContainer();
    runApp(container);
  }

  private static DependencyContainer buildContainer() {
    return new DependencyContainer();
  }

  private static void runApp(final DependencyContainer container) {
    try (final Scanner scanner = new Scanner(System.in)) {
      final ConsoleIO consoleIO = new ConsoleIO(scanner, System.out);
      new UserManagementCli(container.userController(), consoleIO).start();
    }
  }
}