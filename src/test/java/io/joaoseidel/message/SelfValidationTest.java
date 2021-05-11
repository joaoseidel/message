package io.joaoseidel.message;

import io.joaoseidel.message.common.SelfValidating;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SelfValidationTest {

  @Test
  void givenViolations_whenValidatingItSelf_thenThrowConstraintViolationException() {
    assertThatThrownBy(() -> new TestValidation(null))
        .isInstanceOf(ConstraintViolationException.class);
  }

  final class TestValidation extends SelfValidating<TestValidation> {
    @NotNull final String value;

    public TestValidation(@NotNull String value) {
      this.value = value;
      validateSelf();
    }
  }
}
