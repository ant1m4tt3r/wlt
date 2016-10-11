package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import constants.ErrorConstants;


public class SQLUtilities {

  /**
   * @return "yyyy-MM-dd HH:mm:ss"
   */
  public static java.sql.Date getDateSQL() {

    java.sql.Date date = new java.sql.Date((new GregorianCalendar(new Locale("pt", "BR")).getTimeInMillis()));
    return date;

  }

  /***/
  public static final String valueToSQL(String a) {
    if (a != null && a == ErrorConstants.ERROR_STRING)
      return "null";

    if (isWrongOrNull(a))
      return "null";

    return "'" + filter(a) + "'";
  }


  /***/
  public static final String valueToSQL(Integer a) {
    if (a != null && a == ErrorConstants.ERROR_INTEGER)
      return "null";

    if (isWrongOrNull(a))
      return "null";

    return a.toString();
  }


  /***/
  public static final String valueToSQL(Double a) {
    if (a != null && a == ErrorConstants.ERROR_DOUBLE)
      return "null";

    if (isWrongOrNull(a))
      return "null";

    return a.toString();
  }


  /***/
  public static final String valueToSQL(Date a, boolean datetime) {
    if (a == ErrorConstants.ERROR_DATE)
      return "null";

    if (a == null)
      return "null";

    return (new SimpleDateFormat(datetime ? "dd/MM/yy k:mm" : "dd/MM/yyyy")).format(a).replaceFirst("24:", "0:");
  }


  private static String filter(String a) {
    return a.replaceAll("'", "''");
  }


  /***/
  private static final boolean isWrong(String a) {
    return false;
  }


  /***/
  private static final boolean isWrong(Integer a) {
    return a != null && a.equals(-1);
  }


  /***/
  private static final boolean isWrong(Double a) {
    return a != null && a.equals(-999999.0);
  }


  /***/
  private static final boolean isWrong(Date a) {
    return a != null && a.before(new Date(30));
  }


  /***/
  private static final boolean isWrongOrNull(String a) {
    return a == null || isWrong(a);
  }


  /***/
  private static final boolean isWrongOrNull(Integer a) {
    return a == null || isWrong(a);
  }


  /***/
  private static final boolean isWrongOrNull(Double a) {
    return a == null || isWrong(a);
  }


  /***/
  @SuppressWarnings("unused")
  private static final boolean isWrongOrNull(Date a) {
    return a == null || isWrong(a);
  }

}
