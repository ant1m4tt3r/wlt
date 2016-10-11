package utilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import constants.ErrorConstants;

/**
 * This class contains simple static methods String Utilities
 *
 */
public class StringUtilities {

  // TODO
  private static final String keywords[] = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while" };

  String oi = "oi";
  /**
   * 
   * @return Retira as Tgs Html do texto
   * 
   */
  public static String removeHTMLTags(String html) {
    if (html == null || html.isEmpty()) {
      return null;
    }
    html = StringUtilities.desescapeISO(html.replaceAll("\n|\t|\r", "").replaceAll("\\<.*?>", "").trim().replaceAll("\\s+", " "));
    html = StringUtilities.desescapeUTF8(html);
    if (html == null || html.isEmpty()) {
      return null;
    }
    return html.trim();
  }


  /**
   * This method returns the same string containing no accents
   * 
   * @param a
   * @return lower case string containing no accents
   */
  public static String removeAccents(String a) {
    // Lower case
    a = a.replaceAll("�", "a");
    a = a.replaceAll("�", "a");
    a = a.replaceAll("�", "a");
    a = a.replaceAll("�", "a");
    a = a.replaceAll("�", "e");
    a = a.replaceAll("�", "e");
    a = a.replaceAll("�", "e");
    a = a.replaceAll("�", "i");
    a = a.replaceAll("�", "i");
    a = a.replaceAll("�", "i");
    a = a.replaceAll("�", "o");
    a = a.replaceAll("�", "o");
    a = a.replaceAll("�", "o");
    a = a.replaceAll("�", "o");
    a = a.replaceAll("�", "u");
    a = a.replaceAll("�", "u");
    a = a.replaceAll("�", "u");
    a = a.replaceAll("�", "u");
    a = a.replaceAll("�", "c");
    a = a.replaceAll("�", "n");
    // Upper case
    a = a.replaceAll("�", "A");
    a = a.replaceAll("�", "A");
    a = a.replaceAll("�", "A");
    a = a.replaceAll("�", "A");
    a = a.replaceAll("�", "E");
    a = a.replaceAll("�", "E");
    a = a.replaceAll("�", "E");
    a = a.replaceAll("�", "I");
    a = a.replaceAll("�", "I");
    a = a.replaceAll("�", "I");
    a = a.replaceAll("�", "O");
    a = a.replaceAll("�", "O");
    a = a.replaceAll("�", "O");
    a = a.replaceAll("�", "O");
    a = a.replaceAll("�", "U");
    a = a.replaceAll("�", "U");
    a = a.replaceAll("�", "U");
    a = a.replaceAll("�", "U");
    a = a.replaceAll("�", "C");
    a = a.replaceAll("�", "N");

    return a;
  }


  /**
   * Returns the lower case <code>String</code> with no spaces or special characters.
   */
  public static final String formatStringLowerNoSpace(String a) {
    if (a == null || a.isEmpty()) {
      return null;
    }
    a = a.toLowerCase();
    a = removeAccents(a);
    a = a.replaceAll("�", "");
    a = a.replaceAll("/", "");
    a = a.replaceAll("\n", "");
    a = a.replaceAll(" ", "");
    a = a.replaceAll("null", "");
    a = a.replace("-", "");
    a = a.replace("_", "");
    a = a.replace("?", "");
    a = a.replace("(", "");
    a = a.replace(")", "");

    return a;
  }


  /***/
  public static final String normalizaString(String a) {
    if (a == null || a.isEmpty()) {
      return null;
    }

    a = a.replaceAll("\n", "");
    a = a.replaceAll(" ", "");
    a = a.toLowerCase();
    a = removeAccents(a);
    a = a.replaceAll("null", "");
    a = a.replace("-", "").replace("/", "").replace("\\", "").replace("|", "").replace(".", "").replace(",", "").replace(":", "").replace(";", "");
    a = a.replace("!", "").replace("?", "").replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("*", "").replace("#", "");
    a = a.replace("@", "").replace("&", "");

    if (a == null || a.isEmpty()) {
      return null;
    }

    return a;
  }


  /**
   * Removes de scape character <code>\n</code> from the <code>String</code>
   */
  public static final String parseToString(String a) {
    if (a == null || a.isEmpty()) {
      return null;
    }

    a = a.replace("\n", "");
    a = a.trim();

    return a.isEmpty() ? null : a;
  }


  /***/
  public static final String valueToGui(Date a, boolean datetime) {
    if (a == null) {
      return "";
    }

    return new SimpleDateFormat(datetime ? "dd/MM/yyyy k:mm" : "dd/MM/yyyy").format(a);
  }


  /**
   * 
   * @return Compra duas string retirando caracteres especiais e espa�os
   * 
   */
  public static boolean comparaString(String a1, String a2) {
    if (a1 == null || a2 == null)
      return false;

    else if (normalizaString(a1).equalsIgnoreCase(normalizaString(a2)))
      return true;

    return false;
  }


  /**
   * Returns the capitalized <code>String</code> Eg.: capitalize("foo") -> "Foo".
   * 
   * @param t
   * @return capitalized <code>t</code>
   */
  public static String capitalize(String t) {
    if (t == null || t.isEmpty())
      return null;
    char[] array = t.toCharArray();
    array[0] = t.substring(0, 1).toUpperCase().charAt(0);
    return new String(array);
  }


  /**
   * Checks if the keyword is a Java reserved word
   * 
   * @param keyword
   * @return {@code true} if {@code keyword} is a keyword, otherwise {@code false}
   */
  public static boolean isJavaKeyword(String keyword) {
    return (Arrays.binarySearch((keywords), keyword) >= 0);
  }


  /**
   * Checks if the <code>String t</code> starts with a number.
   * 
   * @param t
   * @return {@code true} if {@code keyword} starts, otherwise {@code false}
   */
  public static boolean startsWithNumber(String t) {
    if (Character.isDigit(t.charAt(0)))
      return true;
    return false;
  }


  /**
   * Checks if the <code>String t</code> it's composed of numbers.
   * 
   * @param t
   * @return {@code true} if {@code t} only contains numbers, otherwise {@code false}
   */
  public static boolean onlyNumbers(String t) {
    return t.matches("[0-9]");
  }


  /**
   * Checks if the <code>String t</code> contains special characters except "_" and space(" ","/s").
   * 
   * @param t
   * @return {@code true} if {@code t} contains special characters, otherwise {@code false}
   */
  public static boolean verifySpecials(String t) {
    return Pattern.compile("[^a-z0-9_ ]").matcher(t).find();
  }


  // Date

  public static final String dateToString(Date a) {
    if (a == ErrorConstants.ERROR_DATE)
      return "Data inv�lida";

    if (a == null)
      return null;

    try {
      return new SimpleDateFormat("dd/MM/yyyy").format(a);
    }
    catch (Exception e) {
      return new SimpleDateFormat("dd/MM/yyyy").format(a);
    }
  }


  public static String parseDateSqlString(String date) {
    if (date != null && !date.isEmpty() && date.contains("/")) {
      date = date.trim();
      date = date.replaceAll("\\s+.*", "");
      String temp[] = date.split("/");

      GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[0]));
      java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

      return dateToString(dts);
    }
    else if (date != null && !date.isEmpty()) {

      date = Utilities.somaData("1900-01-01", Utilities.parseToInteger(date) - 2);

      String temp[] = date.split("/");

      GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[0]));
      java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

      return dateToString(dts);
    }
    return null;
  }


  // UTF Utilities
  /**
   * Changes all the special characters of the <code>String</code> by the corresponding UTF8 escape characters
   * @param a
   * @return
   */
  public static final String escapeUTF8(String a) {
    if (a == null) {
      return null;
    }

    String s = new String();
    char c;
    for (int i = 0; i < a.length(); i++) {
      c = a.charAt(i);
      switch (c) {
        case '�':
          s += "&agrave;";
          break;
        case '�':
          s += "&aacute;";
          break;
        case '�':
          s += "&acirc;";
          break;
        case '�':
          s += "&atilde;";
          break;
        case '�':
          s += "&ccedil;";
          break;
        case '�':
          s += "&egrave;";
          break;
        case '�':
          s += "&eacute;";
          break;
        case '�':
          s += "&ecirc;";
          break;
        case '�':
          s += "&igrave;";
          break;
        case '�':
          s += "&iacute;";
          break;
        case '�':
          s += "&icirc;";
          break;
        case '�':
          s += "&ntilde;";
          break;
        case '�':
          s += "&ograve;";
          break;
        case '�':
          s += "&oacute;";
          break;
        case '�':
          s += "&ocirc;";
          break;
        case '�':
          s += "&otilde;";
          break;
        case '�':
          s += "&ugrave;";
          break;
        case '�':
          s += "&uacute;";
          break;
        case '�':
          s += "&ucirc;";
          break;
        case '�':
          s += "&uuml;";
          break;
        case '�':
          s += "&Agrave;";
          break;
        case '�':
          s += "&Aacute;";
          break;
        case '�':
          s += "&Acirc;";
          break;
        case '�':
          s += "&Atilde;";
          break;
        case '�':
          s += "&Ccedil;";
          break;
        case '�':
          s += "&Egrave;";
          break;
        case '�':
          s += "&Eacute;";
          break;
        case '�':
          s += "&Igrave;";
          break;
        case '�':
          s += "&Iacute;";
          break;
        case '�':
          s += "&Icirc;";
          break;
        case '�':
          s += "&Ntilde;";
          break;
        case '�':
          s += "&Ograve;";
          break;
        case '�':
          s += "&Oacute;";
          break;
        case '�':
          s += "&Ocirc;";
          break;
        case '�':
          s += "&Otilde;";
          break;
        case '�':
          s += "&Ugrave;";
          break;
        case '�':
          s += "&Uacute;";
          break;
        case '�':
          s += "&Ucirc;";
          break;
        case '�':
          s += "&Uuml;";
          break;
        case '�':
          s += "&Ecirc;";
          break;
        case '<':
          s += "&lt;";
          break;
        case '>':
          s += "&gt;";
          break;
        case '&':
          s += "&amp;";
          break;
        case '"':
          s += "&quot;";
          break;
        case '�':
          s += "&deg;";
          break;
        case '�':
          s += "&ordm;";
          break;
        case '�':
          s += "&ordf;";
          break;
        default:
          s += String.format("%s", c);
          break;
      }
    }
    return s;
  }

  /**
   * Changes all the special characters of the <code>String</code> by the corresponding UTF8 desescape characters (reverse of <code>escapeUTF8()</code>)
   * @param a
   * @return
   */
  public static final String desescapeUTF8(String s) {
    if (s == null) {
      return null;
    }

    s = s.replace("&agrave;", "�");
    s = s.replace("&aacute;", "�");
    s = s.replace("&acirc;", "�");
    s = s.replace("&atilde", "�");

    s = s.replace("&Agrave;", "�");
    s = s.replace("&Aacute;", "�");
    s = s.replace("&Acirc;", "�");
    s = s.replace("&Atilde;", "�");

    s = s.replace("&ccedil;", "�");
    s = s.replace("&Ccedil;", "�");

    s = s.replace("&egrave;", "�");
    s = s.replace("&eacute;", "�");
    s = s.replace("&ecirc;", "�");

    s = s.replace("&Egrave;", "�");
    s = s.replace("&Eacute;", "�");
    s = s.replace("&Ecirc;", "�");

    s = s.replace("&igrave;", "�");
    s = s.replace("&iacute;", "�");
    s = s.replace("&icirc;", "�");

    s = s.replace("&Igrave;", "�");
    s = s.replace("&Iacute;", "�");
    s = s.replace("&icirc;", "�");

    s = s.replace("&ntilde;", "�");
    s = s.replace("&Ntilde;", "�");

    s = s.replace("&ograve;", "�");
    s = s.replace("&oacute;", "�");
    s = s.replace("&ocirc;", "�");
    s = s.replace("&otilde;", "�");

    s = s.replace("&Ograve;", "�");
    s = s.replace("&Oacute;", "�");
    s = s.replace("&Ocirc;", "�");
    s = s.replace("&Otilde;", "�");

    s = s.replace("&ugrave;", "�");
    s = s.replace("&uacute;", "�");
    s = s.replace("&ucirc;", "�");
    s = s.replace("&uuml;", "u");

    s = s.replace("&Ugrave;", "�");
    s = s.replace("&Uacute;", "�");
    s = s.replace("&Ucirc;", "�");
    s = s.replace("&Uuml;", "U");

    return s;
  }

  /**
   * Changes all the special characters of the <code>String</code> by the corresponding ISO desescape characters
   * @param a
   * @return
   */
  public static String desescapeISO(String s) {

    if (s == null) {
      return null;
    }

    s = s.replace("&#192;", "�");// Capital A, grave accent
    s = s.replace("&#193;", "�");// Capital A, acute accent
    s = s.replace("&#194;", "�");// Capital A, circumflex accent
    s = s.replace("&#195;", "�");// Capital A, tilde
    s = s.replace("&#196;", "�");// Capital A, dieresis or umlaut mark
    s = s.replace("&#197;", "�");// Capital A, ring
    s = s.replace("&#198;", "�");// Capital AE dipthong (ligature)
    s = s.replace("&#199;", "�");// Capital C, cedilla
    s = s.replace("&#200;", "�");// Capital E, grave accent
    s = s.replace("&#201;", "�");// Capital E, acute accent
    s = s.replace("&#202;", "�");// Capital E, circumflex accent
    s = s.replace("&#203;", "�");// Capital E, dieresis or umlaut mark
    s = s.replace("&#204;", "�");// Capital I, grave accent
    s = s.replace("&#205;", "�");// Capital I, acute accent
    s = s.replace("&#206;", "�");// Capital I, circumflex accent
    s = s.replace("&#207;", "�");// Capital I, dieresis or umlaut mark
    s = s.replace("&#208;", "�");// Capital Eth, Icelandic
    s = s.replace("&#209;", "�");// Capital N, tilde
    s = s.replace("&#210;", "�");// Capital O, grave accent
    s = s.replace("&#211;", "�");// Capital O, acute accent
    s = s.replace("&#212;", "�");// Capital O, circumflex accent
    s = s.replace("&#213;", "�");// Capital O, tilde
    s = s.replace("&#214;", "�");// Capital O, dieresis or umlaut mark
    s = s.replace("&#215;", "�");// Multiply sign
    s = s.replace("&#216;", "�");// Capital O, slash
    s = s.replace("&#217;", "�");// Capital U, grave accent
    s = s.replace("&#218;", "�");// Capital U, acute accent
    s = s.replace("&#219;", "�");// Capital U, circumflex accent
    s = s.replace("&#220;", "�");// Capital U, dieresis or umlaut mark
    s = s.replace("&#221;", "�");// Capital Y, acute accent
    s = s.replace("&#222;", "�");// Capital THORN, Icelandic
    s = s.replace("&#223;", "�");// Small sharp s, German (sz ligature)
    s = s.replace("&#224;", "�");// Small a, grave accent
    s = s.replace("&#225;", "�");// Small a, acute accent
    s = s.replace("&#226;", "�");// Small a, circumflex accent
    s = s.replace("&#227;", "�");// Small a, tilde
    s = s.replace("&#228;", "�");// Small a, dieresis or umlaut mark
    s = s.replace("&#229;", "�");// Small a, ring
    s = s.replace("&#230;", "�");// Small ae dipthong (ligature)
    s = s.replace("&#231;", "�");// Small c, cedilla
    s = s.replace("&#232;", "�");// Small e, grave accent
    s = s.replace("&#233;", "�");// Small e, acute accent
    s = s.replace("&#234;", "�");// Small e, circumflex accent
    s = s.replace("&#235;", "�");// Small e, dieresis or umlaut mark
    s = s.replace("&#236;", "�");// Small i, grave accent
    s = s.replace("&#237;", "�");// Small i, acute accent
    s = s.replace("&#238;", "�");// Small i, circumflex accent
    s = s.replace("&#239;", "�");// Small i, dieresis or umlaut mark
    s = s.replace("&#240;", "�");// Small eth, Icelandic
    s = s.replace("&#241;", "�");// Small n, tilde
    s = s.replace("&#242;", "�");// Small o, grave accent
    s = s.replace("&#243;", "�");// Small o, acute accent
    s = s.replace("&#244;", "�");// Small o, circumflex accent
    s = s.replace("&#245;", "�");// Small o, tilde
    s = s.replace("&#246;", "�");// Small o, dieresis or umlaut mark
    s = s.replace("&#247;", "�");// Division sign
    s = s.replace("&#248;", "�");// Small o, slash
    s = s.replace("&#249;", "�");// Small u, grave accent
    s = s.replace("&#250;", "�");// Small u, acute accent
    s = s.replace("&#251;", "�");// Small u, circumflex accent
    s = s.replace("&#252;", "�");// Small u, dieresis or umlaut mark
    s = s.replace("&#253;", "�");// Small y, acute accent
    s = s.replace("&#254;", "�");// Small thorn, Icelandic
    s = s.replace("&#255;", "�");// Small y, dieresis or umlaut mark

    return s;

  }

}
