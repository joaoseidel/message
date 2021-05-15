package io.joaoseidel.message;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.joaoseidel.message.common.SelfValidating;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

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
