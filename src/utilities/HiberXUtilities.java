package utilities;

import static utilities.StringUtilities.capitalize;
import static utilities.StringUtilities.formatStringLowerNoSpace;

public class HiberXUtilities {

  /**
   * 
   * @param name
   * @return the name of the Set Method corresponding to the name and following the lowerCamelCase convention.
   */
  public static final String getSetMethod(String name) {
    return name == null ? null : "set" + capitalize(name);
  }


  /**
   * 
   * @param name
   * @return the name of the Get Method corresponding to the name and following the lowerCamelCase convention.
   */
  public static final String getGetMethod(String name) {
    return name == null ? null : "get" + capitalize(name);
  }


  /**
   * Returns the name of the bean following first letter upper case convention.
   * 
   * @param name
   * @return
   */
  public static final String getName(String name) {
    name = name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf("."));
    name = capitalize(formatStringLowerNoSpace(name));
    return name;
  }

}
