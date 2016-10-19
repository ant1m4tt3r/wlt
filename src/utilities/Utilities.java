package utilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import constants.ErrorConstants;

public class Utilities {

  // General Utilities

  /**
   * 
   * @param input
   * @return String containing the md5 of the <code>input</code>
   */
  public static String generateMD5(String input) {
    String md5 = null;
    if (null == input)
      return null;
    try {
      // Create MessageDigest object for MD5
      MessageDigest digest = MessageDigest.getInstance("MD5");
      // Update input string in message digest
      digest.update(input.getBytes(), 0, input.length());
      // Converts message digest value in base 16 (hex)
      md5 = new BigInteger(1, digest.digest()).toString(16);
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return md5;
  }


  /**
   * This method returns the List<String> without empty or null values.
   * 
   * @param titles
   * @return
   */
  public static List<String> removeEmptyNulls(List<String> titles) {
    List<String> refined = new ArrayList<String>();
    for (String t : titles) {
      if (t == null || t.isEmpty()) {
        continue;
      }
      refined.add(t.replace("\\s+", "_"));
    }
    return refined;

  }


  // Parse Utilities

  /**
   * returns an Integer containing the value of the string
   * 
   * @return Converte String para Inteiro arredondando para baixo sempre
   */
  public static Integer parseToInteger(String data) {

    if (data == null || data.isEmpty()) {
      return null;
    }
    data = data.replace(",", ".");
    if (data.contains(".")) {
      data = data.substring(0, data.indexOf("."));
    }
    try {
      Integer n = Integer.parseInt(data);
      return n;
    }
    catch (Exception e) {
      return ErrorConstants.ERROR_INTEGER;
    }
  }


  /**
   * This method actually returns an Integer, 1 = true, 0 = false This is done so beacuse if an exception it's catched in the parsing, it will return
   * an error constant {@code ERROR_INTEGER}
   * 
   */
  public static final Integer parseToBoolean(String data) {

    if (data == null || data.isEmpty()) {
      return null;
    }
    data = data.replaceAll(" ", "");
    if ((data != null) && (!data.isEmpty())) {
      data = data.toLowerCase();
      if (data.equals("verdadeiro") || data.equals("true")) {
        return 1;
      }
      if (data.equals("falso") || data.equals("false")) {
        return 0;
      }
    }
    return ErrorConstants.ERROR_INTEGER;

  }


  /**
   * returns a Double containing the value of the string
   */
  public static final Double parseToDouble(String data) {
    if (data == null || data.isEmpty() || data.equals("null") || data.equalsIgnoreCase("na"))
      return null;
    data = data.replace(",", ".");
    try {
      Double n = Double.parseDouble(data);
      return n;
    }
    catch (NumberFormatException e) {
      return ErrorConstants.ERROR_DOUBLE;
    }

  }


  // Interface Utilities
  /**
   * this method receives any {@code Component} and centralizes it on the screen
   * 
   * @param frame
   */
  public static void centralizarComponente(Component frame) {
    Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dw = frame.getSize();
    frame.setLocation((ds.width - dw.width) / 2, (ds.height - dw.height - 20) / 2);
  }


  // Date Utilities

  /**
   * 
   * @param format
   *          of the date Eg.: "dd/MM/yyyy HH:mm:ss"
   * @return String containing the date formated by the format input
   */
  public static String getDate(String format) {
    DateFormat dateFormat = new SimpleDateFormat(format);
    Date date = new Date();
    return dateFormat.format(date);
  }


  /**
   * @return String "dd/MM/yyyy HH:mm:ss"
   */
  public static String getDate() {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }


  /**
   * @return String "yyyyMMddHHmmss"
   */
  public static String getDateName() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date).replaceAll("/", "").replaceAll(":", "").replaceAll("\\s+", "");
  }


  /**
   * 
   * @return phrase containing the date in the brazilian format Eg.:"1 de julho de 2016."
   */
  public static String getLongDate() {
    Calendar dt = Calendar.getInstance();
    // retorna os valores dia, mês e ano da variável "dt"
    int d = dt.get(Calendar.DATE);
    int m = dt.get(Calendar.MONTH) + 1;
    int y = dt.get(Calendar.YEAR);

    // retorna o dia da semana: 1=domingo, 2=segunda-feira,
    // ..., 7=sábado
    return (d + " de " + monthName(m) + " de " + y + ".");
  }


  /**
   * 
   * @param i
   *          = month index (january = 1, february =2, ...)
   * @return the name of the month in portuguese
   */
  public static String monthName(int i) {
    String m[] = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro" };
    return (m[i - 1]);
  }


  /**
   * 
   * @param i
   *          = month index (jan =1, feb = 2, ...)
   * @return the short name of the month in portuguese
   */
  public static String shortMonthName(int i) {
    String m[] = { "jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez" };
    return (m[i - 1]);
  }


  /**
   * @return "Converte valor de hora estourado do XLS em hora HH:mm:ss"
   */
  public static String converteHoraXls(String hora) {

    if (hora == null || hora.isEmpty())
      return null;

    else if (hora.contains(":")) {
      String temp[] = hora.split(":");

      for (int i = 0; i < temp.length; i++) {
        if (i == 0 && parseToDouble(temp[i]) > 23) {
          return hora + " @";
        }
        else if (parseToDouble(temp[i]) > 59) {
          return hora + " @";
        }

      }

      return hora;

    }

    else if (hora.contains(".")) {
      String horaCompleta;

      Double horaTemp = 24 * parseToDouble(hora);

      if (horaTemp.intValue() < 10)
        horaCompleta = String.format("0%s:", horaTemp.intValue());
      else
        horaCompleta = String.format("%s:", horaTemp.intValue());

      BigDecimal bd = new BigDecimal(horaTemp);
      bd = bd.setScale(15, BigDecimal.ROUND_HALF_UP);
      Double minTemp = 60 * (bd.doubleValue() - bd.intValue());

      bd = new BigDecimal(minTemp);
      bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

      if (bd.intValue() < 10)
        horaCompleta = String.format("%s0%s:", horaCompleta, bd.intValue());
      else
        horaCompleta = String.format("%s%s:", horaCompleta, bd.intValue());

      bd = new BigDecimal(minTemp);
      bd = bd.setScale(15, BigDecimal.ROUND_HALF_UP);
      Double segTemp = 60 * (bd.doubleValue() - bd.intValue());

      bd = new BigDecimal(segTemp);
      bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

      if (bd.intValue() == 60)
        bd = new BigDecimal(0);

      if (bd.intValue() < 10)
        horaCompleta = String.format("%s0%s", horaCompleta, bd.intValue());
      else
        horaCompleta = String.format("%s%s", horaCompleta, bd.intValue());

      if (horaTemp > 23 || minTemp > 59 || bd.intValue() > 59) {
        horaCompleta = horaCompleta + " @";
      }

      return horaCompleta;

    }

    else
      hora += " @";

    return hora;

  }


  public static String parseDateToTimestamp(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    return dateFormat.format(date);
  }


  public static String parseDateToString(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    return format.format(date);
  }


  public static java.sql.Date parseDateSqlDMY(String date) {
    if (date != null && !date.isEmpty() && date.contains("/")) {
      date = date.trim();
      date = date.replaceAll("\\s+.*", "");
      String temp[] = date.split("/");

      GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[0]));
      java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

      return dts;
    }
    else if (date != null && !date.isEmpty()) {

      date = somaData("1900-01-01", parseToInteger(date) - 2);

      String temp[] = date.split("/");

      GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[0]));
      java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

      return dts;
    }
    return null;
  }


  /**
   * Verifys if the <code>String date</code> is valid, checking day, months, year and bissextile verification. If the date also contains time, verifys
   * minutes and hours aswell.
   *
   */
  // falta verificar se a data eh valida
  public static final Date parseToDateDMY(String date) {
    if (date == null)
      return null;

    DateFormat dt = null;
    if ((date == null) || date.isEmpty()) {
      return null;
    }
    date = date.trim();
    if (!date.matches("[0-9]+/[0-9]+/[0-9]+ [0-9]*:[0-9]*") && (!date.matches("[0-9]+/[0-9]+/[0-9]+")) && !date.matches("[0-2]+/[0-9]") && (!date.matches("[0-2]+/[0-9]+[0-9]")))
      return ErrorConstants.ERROR_DATE;

    int indexFirstBar = date.indexOf("/");
    int indexSecondBar = date.lastIndexOf("/");
    int indexTwoPoints = -1;
    int indexSpace = -1;

    if (indexFirstBar == indexSecondBar) {
      date = "01/" + date;
      indexFirstBar = date.indexOf("/");
      indexSecondBar = date.lastIndexOf("/");
    }
    if (date.contains(" ")) {
      indexSpace = date.indexOf(" ");
      indexTwoPoints = date.indexOf(":");
    }

    // verify year
    int year = 0;
    if (indexSpace != -1)
      year = Integer.parseInt(date.substring(indexSecondBar + 1, indexSpace));
    else
      year = Integer.parseInt(date.substring(indexSecondBar + 1));
    if (year < 15) {
      year = year + 2000;
    }
    if ((year < 1965) || (year > 2020)) {
      return ErrorConstants.ERROR_DATE;
    }

    // verify bissextile
    boolean bissextile = false;
    if ((!(year % 4 > 0)) && ((year % 100 > 0) || (!(year % 400 > 0)))) {
      bissextile = true;
    }

    int month = Integer.parseInt(date.substring(indexFirstBar + 1, indexSecondBar));
    if ((month < 1) || (month > 12)) {
      return ErrorConstants.ERROR_DATE;
    }

    // verify day
    int day = Integer.parseInt(date.substring(0, indexFirstBar));
    if (day < 1) {
      return ErrorConstants.ERROR_DATE;
    }

    if (month == 2) {
      if (bissextile) {
        if (day > 29) {
          return ErrorConstants.ERROR_DATE;
        }
      }
      else if (day > 28) {
        return ErrorConstants.ERROR_DATE;
      }
    }
    else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
      if (day > 30) {
        return ErrorConstants.ERROR_DATE;
      }
    }
    else if (day > 31) {
      return ErrorConstants.ERROR_DATE;
    }

    // verifying if it has time
    if (date.contains(" ")) {
      // verifying hours
      int hour = Integer.parseInt(date.substring(indexSpace + 1, indexTwoPoints));
      if (hour < 0 || hour > 23) {
        return ErrorConstants.ERROR_DATE;
      }

      // verifying minutes
      int minute = Integer.parseInt(date.substring(indexTwoPoints + 1));
      if (minute < 0 || minute > 59)
        return ErrorConstants.ERROR_DATE;

      dt = new SimpleDateFormat("dd/MM/yy kk:mm");
    }
    else {
      dt = new SimpleDateFormat("dd/MM/yy");
    }
    try {
      return dt.parse(date);
    }
    catch (ParseException e) {
      return ErrorConstants.ERROR_DATE;
    }

  }


  public static final Date parseTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    // com set lenient a data não se adapta a numeros
    // muito grandes. Se não setado, por exemplo ao
    // inserir 25h a data ficaria como 1h da manhã
    // do dia seguinte.
    sdf.setLenient(false);
    try {
      return sdf.parse(time);
    }
    catch (ParseException e) {
      return ErrorConstants.ERROR_DATE;
    }

  }


  /**
   * Se a data vier separada por "-" Será trocado por "/"
   */
  public static String verificaMascaraData(String data) {
    for (int i = 0; i < data.length(); i++) {
      char c = data.charAt(i);
      if (c == '-')
        data = data.replace('-', '/');
    }
    return data;
  }


  @SuppressWarnings("deprecation")
  public static String somaData(String data, int somaDias) {

    Date dt = new Date(verificaMascaraData(data));
    String formato = "dd/MM/yyyy";
    dt.setDate(dt.getDate() + somaDias);

    SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
    return dataFormatada.format(dt);
  }


  public static int intervaloDias(Date d1, Date d2) {
    int result = (int) ((d1.getTime() - d2.getTime()) / 86400000L);
    return result < 0 ? result * -1 : result;
  }

}
