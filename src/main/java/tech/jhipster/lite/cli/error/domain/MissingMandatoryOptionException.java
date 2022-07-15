package tech.jhipster.lite.cli.error.domain;

public class MissingMandatoryOptionException extends AssertionException {

  private MissingMandatoryOptionException(String message) {
    super(message);
  }

  public static MissingMandatoryOptionException forBlankValue(String option) {
    return new MissingMandatoryOptionException(defaultMessage(option, "blank"));
  }

  public static MissingMandatoryOptionException forNullValue(String option) {
    return new MissingMandatoryOptionException(defaultMessage(option, "null"));
  }

  public static MissingMandatoryOptionException forEmptyValue(String option) {
    return new MissingMandatoryOptionException(defaultMessage(option, "empty"));
  }

  private static String defaultMessage(String field, String reason) {
    return new StringBuilder()
      .append("The option \"")
      .append(field)
      .append("\" is mandatory and wasn't set")
      .append(" (")
      .append(reason)
      .append(")")
      .toString();
  }
}
