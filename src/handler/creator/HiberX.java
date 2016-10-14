package handler.creator;

import static utilities.StringUtilities.capitalize;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Incompleta!
 * 
 * @author hugo
 *
 */
public class HiberX {

  private static final ClassLoader classLoader = HiberX.class.getClassLoader();
  
  /**
   * This mehtod uses a reflection routine to create intances of the <code>classe</code> with the corresponding values.
   * @param classe
   * @param titles
   * @param values
   * @return
   */
  public static Object fill(Class<?> classe, List<String> titles, List<String> values) {
    Object o = null;
    try {
      Class<?> loaded = classLoader.loadClass(classe.getCanonicalName());
      o = loaded.newInstance();
      for (int i = 0; i < titles.size(); i++) {
        if (titles.get(i) != null) {
          String title = titles.get(i);
          loaded.getMethod("set" + capitalize(title), String.class).invoke(o, values.get(i));
        }
      }
    }
    catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      System.err.println("Classe não encontada: " + classe.getName());
      e.printStackTrace();
    }

    return o;
  }

}
