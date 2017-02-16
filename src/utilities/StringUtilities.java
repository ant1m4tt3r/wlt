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

	/**
	 * This <code>String[]</code> contains all java keywords.
	 */
	private static final String keywords[] = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final",
			"finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
			"void", "volatile", "while" };

	/**
	 * Private constructor, no one can instantiate this class.
	 */
	private StringUtilities() {
	}

	/**
	 * Returns an <code>String[]</code> containing all java keywords.
	 * 
	 * @return an <code>String[]</code> with all java keywords.
	 */
	public static final String[] getKeywords() {
		return keywords;
	}

	/**
	 * Removes all html tags from the <code>String html</code>.
	 * 
	 * @return A {@code String} without any html tag.
	 * 
	 */
	public static String removeHTMLTags(String html) {
		if (html == null || html.isEmpty()) {
			return null;
		}
		html = StringUtilities
				.desescapeISO(html.replaceAll("\n|\t|\r", "").replaceAll("\\<.*?>", "").trim().replaceAll("\\s+", " "));
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
		a = a.replaceAll("Ã¡", "a");
		a = a.replaceAll("Ã ", "a");
		a = a.replaceAll("Ã¢", "a");
		a = a.replaceAll("Ã£", "a");
		a = a.replaceAll("Ã©", "e");
		a = a.replaceAll("Ã¨", "e");
		a = a.replaceAll("Ãª", "e");
		a = a.replaceAll("Ã­", "i");
		a = a.replaceAll("Ã¬", "i");
		a = a.replaceAll("Ã®", "i");
		a = a.replaceAll("Ã³", "o");
		a = a.replaceAll("Ã²", "o");
		a = a.replaceAll("Ã´", "o");
		a = a.replaceAll("Ãµ", "o");
		a = a.replaceAll("Ãº", "u");
		a = a.replaceAll("Ã¹", "u");
		a = a.replaceAll("Ã»", "u");
		a = a.replaceAll("Ã¼", "u");
		a = a.replaceAll("Ã§", "c");
		a = a.replaceAll("Ã±", "n");
		// Upper case
		a = a.replaceAll("Ã�", "A");
		a = a.replaceAll("Ã€", "A");
		a = a.replaceAll("Ã‚", "A");
		a = a.replaceAll("Ãƒ", "A");
		a = a.replaceAll("Ã‰", "E");
		a = a.replaceAll("Ãˆ", "E");
		a = a.replaceAll("ÃŠ", "E");
		a = a.replaceAll("Ã�", "I");
		a = a.replaceAll("ÃŒ", "I");
		a = a.replaceAll("ÃŽ", "I");
		a = a.replaceAll("Ã“", "O");
		a = a.replaceAll("Ã’", "O");
		a = a.replaceAll("Ã”", "O");
		a = a.replaceAll("Ã•", "O");
		a = a.replaceAll("Ãš", "U");
		a = a.replaceAll("Ã™", "U");
		a = a.replaceAll("Ã›", "U");
		a = a.replaceAll("Ãœ", "U");
		a = a.replaceAll("Ã‡", "C");
		a = a.replaceAll("Ã‘", "N");

		return a;
	}

	/**
	 * Removes all special characters from the input <code>String</code> (no
	 * spaces);
	 * 
	 * @see HiberXUtilities
	 * @param input
	 * @return string with no special characters.
	 */
	public static final String removeSpecials(String input) {
		return input.replaceAll("[^a-zA-Z0-9]+", "");
	}

	/**
	 * Returns the lower case <code>String</code> with no spaces or special
	 * characters.
	 */
	public static final String formatStringLowerNoSpace(String a) {
		if (a == null || a.isEmpty()) {
			return null;
		}
		a = a.toLowerCase();
		a = removeAccents(a);
		a = a.replaceAll("Â°", "");
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
		a = a.replace("-", "").replace("/", "").replace("\\", "").replace("|", "").replace(".", "").replace(",", "")
				.replace(":", "").replace(";", "");
		a = a.replace("!", "").replace("?", "").replace("[", "").replace("]", "").replace("(", "").replace(")", "")
				.replace("*", "").replace("#", "");
		a = a.replace("@", "").replace("&", "");

		if (a == null || a.isEmpty()) {
			return null;
		}

		return a;
	}

	/**
	 * Removes the escape character <code>\n</code> from the <code>String</code>
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
	 * Compares <code>a1</code> and <code>a2</code> without considering special
	 * characters or spaces.
	 * 
	 * @return <code>true</code> if a1 matches a2. <code>false</code> otherwise.
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
	 * Returns the capitalized <code>String</code> Eg.: capitalize("foo") ->
	 * "Foo".
	 * 
	 * @param t
	 * @return capitalized <code>t</code>
	 */
	public static String capitalize(String t) {
		if (t == null || t.isEmpty())
			return t;
		char[] array = t.toCharArray();
		array[0] = t.substring(0, 1).toUpperCase().charAt(0);
		return new String(array);
	}

	/**
	 * Checks if the keyword is a Java reserved word
	 * 
	 * @param keyword
	 * @return {@code true} if {@code keyword} is a keyword, otherwise
	 *         {@code false}
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
	 * @return {@code true} if {@code t} only contains numbers, otherwise
	 *         {@code false}
	 */
	public static boolean onlyNumbers(String t) {
		return t.matches("[0-9]");
	}

	/**
	 * Checks if the <code>String t</code> contains special characters except
	 * "_" and space(" ","/s").
	 * 
	 * @param t
	 * @return {@code true} if {@code t} contains special characters, otherwise
	 *         {@code false}
	 */
	public static boolean verifySpecials(String t) {
		return Pattern.compile("[^a-z0-9_ ]").matcher(t).find();
	}

	// Date

	public static final String dateToString(Date a) {
		if (a == ErrorConstants.ERROR_DATE)
			return "Data invÃ¡lida";

		if (a == null)
			return null;

		try {
			return new SimpleDateFormat("dd/MM/yyyy").format(a);
		} catch (Exception e) {
			return new SimpleDateFormat("dd/MM/yyyy").format(a);
		}
	}

	public static String parseDateSqlString(String date) {
		if (date != null && !date.isEmpty() && date.contains("/")) {
			date = date.trim();
			date = date.replaceAll("\\s+.*", "");
			String temp[] = date.split("/");

			GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1,
					Integer.parseInt(temp[0]));
			java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

			return dateToString(dts);
		} else if (date != null && !date.isEmpty()) {

			date = Utilities.somaData("1900-01-01", Utilities.parseToInteger(date) - 2);

			String temp[] = date.split("/");

			GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]) - 1,
					Integer.parseInt(temp[0]));
			java.sql.Date dts = new java.sql.Date(gc.getTime().getTime());

			return dateToString(dts);
		}
		return null;
	}

	// UTF Utilities
	/**
	 * Changes all the special characters of the <code>String</code> by the
	 * corresponding UTF8 escape characters
	 * 
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
			case 'à':
				s += "&agrave;";
				break;
			case 'á':
				s += "&aacute;";
				break;
			case 'â':
				s += "&acirc;";
				break;
			case 'ã':
				s += "&atilde;";
				break;
			case 'ç':
				s += "&ccedil;";
				break;
			case 'è':
				s += "&egrave;";
				break;
			case 'é':
				s += "&eacute;";
				break;
			case 'ê':
				s += "&ecirc;";
				break;
			case 'ì':
				s += "&igrave;";
				break;
			case 'í':
				s += "&iacute;";
				break;
			case 'î':
				s += "&icirc;";
				break;
			case 'ñ':
				s += "&ntilde;";
				break;
			case 'ò':
				s += "&ograve;";
				break;
			case 'ó':
				s += "&oacute;";
				break;
			case 'ô':
				s += "&ocirc;";
				break;
			case 'õ':
				s += "&otilde;";
				break;
			case 'ù':
				s += "&ugrave;";
				break;
			case 'ú':
				s += "&uacute;";
				break;
			case 'û':
				s += "&ucirc;";
				break;
			case 'ü':
				s += "&uuml;";
				break;
			case 'À':
				s += "&Agrave;";
				break;
			case 'Á':
				s += "&Aacute;";
				break;
			case 'Â':
				s += "&Acirc;";
				break;
			case 'Ã':
				s += "&Atilde;";
				break;
			case 'Ç':
				s += "&Ccedil;";
				break;
			case 'È':
				s += "&Egrave;";
				break;
			case 'É':
				s += "&Eacute;";
				break;
			case 'Ì':
				s += "&Igrave;";
				break;
			case 'Í':
				s += "&Iacute;";
				break;
			case 'Î':
				s += "&Icirc;";
				break;
			case 'Ñ':
				s += "&Ntilde;";
				break;
			case 'Ò':
				s += "&Ograve;";
				break;
			case 'Ó':
				s += "&Oacute;";
				break;
			case 'Ô':
				s += "&Ocirc;";
				break;
			case 'Õ':
				s += "&Otilde;";
				break;
			case 'Ù':
				s += "&Ugrave;";
				break;
			case 'Ú':
				s += "&Uacute;";
				break;
			case 'Û':
				s += "&Ucirc;";
				break;
			case 'Ü':
				s += "&Uuml;";
				break;
			case 'Ê':
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
			case '°':
				s += "&deg;";
				break;
			case 'º':
				s += "&ordm;";
				break;
			case 'ª':
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
	 * Changes all the special characters of the <code>String s</code> by the
	 * corresponding UTF8 escape characters (reverse of
	 * <code>escapeUTF8()</code>)
	 * 
	 * @param a
	 * @return
	 */
	public static final String desescapeUTF8(String s) {
		if (s == null) {
			return null;
		}

		s = s.replace("&agrave;", "à");
		s = s.replace("&aacute;", "á");
		s = s.replace("&acirc;", "â");
		s = s.replace("&atilde", "ã");

		s = s.replace("&Agrave;", "À");
		s = s.replace("&Aacute;", "Á");
		s = s.replace("&Acirc;", "Â");
		s = s.replace("&Atilde;", "Ã");

		s = s.replace("&ccedil;", "ç");
		s = s.replace("&Ccedil;", "Ç");

		s = s.replace("&egrave;", "è");
		s = s.replace("&eacute;", "é");
		s = s.replace("&ecirc;", "ê");

		s = s.replace("&Egrave;", "È");
		s = s.replace("&Eacute;", "É");
		s = s.replace("&Ecirc;", "Ê");

		s = s.replace("&igrave;", "ì");
		s = s.replace("&iacute;", "í");
		s = s.replace("&icirc;", "î");

		s = s.replace("&Igrave;", "Ì");
		s = s.replace("&Iacute;", "Í");
		s = s.replace("&icirc;", "Î");

		s = s.replace("&ntilde;", "ñ");
		s = s.replace("&Ntilde;", "Ñ");

		s = s.replace("&ograve;", "ò");
		s = s.replace("&oacute;", "ó");
		s = s.replace("&ocirc;", "ô");
		s = s.replace("&otilde;", "õ");

		s = s.replace("&Ograve;", "Ò");
		s = s.replace("&Oacute;", "Ó");
		s = s.replace("&Ocirc;", "Ô");
		s = s.replace("&Otilde;", "Õ");

		s = s.replace("&ugrave;", "ù");
		s = s.replace("&uacute;", "ú");
		s = s.replace("&ucirc;", "û");
		s = s.replace("&uuml;", "u");

		s = s.replace("&Ugrave;", "Ù");
		s = s.replace("&Uacute;", "Ú");
		s = s.replace("&Ucirc;", "Û");
		s = s.replace("&Uuml;", "U");

		return s;
	}

	/**
	 * Changes all the special characters of the <code>String</code> by the
	 * corresponding ISO escape characters
	 * 
	 * @param a
	 * @return
	 */
	public static String desescapeISO(String s) {

		if (s == null) {
			return null;
		}

		s = s.replace("&#192;", "À");// Capital A, grave accent
		s = s.replace("&#193;", "Á");// Capital A, acute accent
		s = s.replace("&#194;", "Â");// Capital A, circumflex accent
		s = s.replace("&#195;", "Ã");// Capital A, tilde
		s = s.replace("&#196;", "Ä");// Capital A, dieresis or umlaut mark
		s = s.replace("&#197;", "Å");// Capital A, ring
		s = s.replace("&#198;", "Æ");// Capital AE dipthong (ligature)
		s = s.replace("&#199;", "Ç");// Capital C, cedilla
		s = s.replace("&#200;", "È");// Capital E, grave accent
		s = s.replace("&#201;", "É");// Capital E, acute accent
		s = s.replace("&#202;", "Ê");// Capital E, circumflex accent
		s = s.replace("&#203;", "Ë");// Capital E, dieresis or umlaut mark
		s = s.replace("&#204;", "Ì");// Capital I, grave accent
		s = s.replace("&#205;", "Í");// Capital I, acute accent
		s = s.replace("&#206;", "Î");// Capital I, circumflex accent
		s = s.replace("&#207;", "Ï");// Capital I, dieresis or umlaut mark
		s = s.replace("&#208;", "Ð");// Capital Eth, Icelandic
		s = s.replace("&#209;", "Ñ");// Capital N, tilde
		s = s.replace("&#210;", "Ò");// Capital O, grave accent
		s = s.replace("&#211;", "Ó");// Capital O, acute accent
		s = s.replace("&#212;", "Ô");// Capital O, circumflex accent
		s = s.replace("&#213;", "Õ");// Capital O, tilde
		s = s.replace("&#214;", "Ö");// Capital O, dieresis or umlaut mark
		s = s.replace("&#215;", "×");// Multiply sign
		s = s.replace("&#216;", "Ø");// Capital O, slash
		s = s.replace("&#217;", "Ù");// Capital U, grave accent
		s = s.replace("&#218;", "Ú");// Capital U, acute accent
		s = s.replace("&#219;", "Û");// Capital U, circumflex accent
		s = s.replace("&#220;", "Ü");// Capital U, dieresis or umlaut mark
		s = s.replace("&#221;", "Ý");// Capital Y, acute accent
		s = s.replace("&#222;", "Þ");// Capital THORN, Icelandic
		s = s.replace("&#223;", "ß");// Small sharp s, German (sz ligature)
		s = s.replace("&#224;", "à");// Small a, grave accent
		s = s.replace("&#225;", "á");// Small a, acute accent
		s = s.replace("&#226;", "â");// Small a, circumflex accent
		s = s.replace("&#227;", "ã");// Small a, tilde
		s = s.replace("&#228;", "ä");// Small a, dieresis or umlaut mark
		s = s.replace("&#229;", "å");// Small a, ring
		s = s.replace("&#230;", "æ");// Small ae dipthong (ligature)
		s = s.replace("&#231;", "ç");// Small c, cedilla
		s = s.replace("&#232;", "è");// Small e, grave accent
		s = s.replace("&#233;", "é");// Small e, acute accent
		s = s.replace("&#234;", "ê");// Small e, circumflex accent
		s = s.replace("&#235;", "ë");// Small e, dieresis or umlaut mark
		s = s.replace("&#236;", "ì");// Small i, grave accent
		s = s.replace("&#237;", "í");// Small i, acute accent
		s = s.replace("&#238;", "î");// Small i, circumflex accent
		s = s.replace("&#239;", "ï");// Small i, dieresis or umlaut mark
		s = s.replace("&#240;", "ð");// Small eth, Icelandic
		s = s.replace("&#241;", "ñ");// Small n, tilde
		s = s.replace("&#242;", "ò");// Small o, grave accent
		s = s.replace("&#243;", "ó");// Small o, acute accent
		s = s.replace("&#244;", "ô");// Small o, circumflex accent
		s = s.replace("&#245;", "õ");// Small o, tilde
		s = s.replace("&#246;", "ö");// Small o, dieresis or umlaut mark
		s = s.replace("&#247;", "÷");// Division sign
		s = s.replace("&#248;", "ø");// Small o, slash
		s = s.replace("&#249;", "ù");// Small u, grave accent
		s = s.replace("&#250;", "ú");// Small u, acute accent
		s = s.replace("&#251;", "û");// Small u, circumflex accent
		s = s.replace("&#252;", "ü");// Small u, dieresis or umlaut mark
		s = s.replace("&#253;", "ý");// Small y, acute accent
		s = s.replace("&#254;", "þ");// Small thorn, Icelandic
		s = s.replace("&#255;", "ÿ");// Small y, dieresis or umlaut mark

		return s;

	}

}
