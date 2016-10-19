package utilities;

import static utilities.StringUtilities.capitalize;

public class HiberXUtilities {

  /**
   * 
   * @param name
   * @return the name of the Set Method corresponding to the name and following the lowerCamelCase convention.
   */
  public static final String getSetMethod(String name) {
    return name == null ? null: "set" + capitalize(name);
  }


  /**
   * 
   * @param name
   * @return the name of the Get Method corresponding to the name and following the lowerCamelCase convention.
   */
  public static final String getGetMethod(String name) {
    return name == null ? null: "get" + capitalize(name);
  }

}
