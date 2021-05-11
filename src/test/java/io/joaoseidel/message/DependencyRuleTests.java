package io.joaoseidel.message;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import io.joaoseidel.message.archunit.HexagonalArchitecture;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyRuleTests {
  @Test
  void validateRegistrationContextArchitecture() {
    HexagonalArchitecture.boundedContext("io.joaoseidel.message")
        .withDomainLayer("domain")
        .withAdaptersLayer("adapter")
        .incoming("web.controller")
        .outgoing("persistence")
        .and()
        .withApplicationLayer("application")
        .services("service")
        .incomingPorts("port.in")
        .outgoingPorts("port.out")
        .and()
        .check(new ClassFileImporter().importPackages("io.joaoseidel.message.."));
  }

  @Test
  void testPackageDependencies() {
    noClasses()
        .that()
        .resideInAPackage("io.joaoseidel.message.domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("io.joaoseidel.message.application..")
        .check(new ClassFileImporter().importPackages("io.joaoseidel.message.."));
  }
}
