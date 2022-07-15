package tech.jhipster.lite.cli.error.domain;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.cli.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
class MissingMandatoryOptionExceptionTest {

  public static final String FIELD = "option";

  @Test
  void shouldGetExceptionForBlankValue() {
    MissingMandatoryOptionException exception = MissingMandatoryOptionException.forBlankValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The option \"option\" is mandatory and wasn't set (blank)");
  }

  @Test
  void shouldGetExceptionForNullValue() {
    MissingMandatoryOptionException exception = MissingMandatoryOptionException.forNullValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The option \"option\" is mandatory and wasn't set (null)");
  }

  @Test
  void shouldGetExceptionForEmptyCollection() {
    MissingMandatoryOptionException exception = MissingMandatoryOptionException.forEmptyValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The option \"option\" is mandatory and wasn't set (empty)");
  }
}
