package constants;

/**
 * <p>
 * This class contains all the error constants provided for the API. All of the security check after parsing should use this constants for evaluation.
 * <p>
 * Be careful when using the {@code ERROR_STRING} constant, it's a {@code String} with the "<Error>" value, so if your text or string contains this
 * data, remove or replace it before comparing.
 * 
 * @author Hugo
 *
 */
public class ErrorConstants {

  // Parse Error constants
  /**
   * <code>ERROR_DATE</code> it's used for parsing dates and avoiding {@code NullPointerException}.
   */
  public static final java.util.Date ERROR_DATE = new java.util.Date(0L);
  public static final double ERROR_DOUBLE = -999999999D;
  public static final int ERROR_INTEGER = -999999999;
  public static final String ERROR_STRING = "<Error>";

}
