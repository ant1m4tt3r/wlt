package handler.creator;

import static utilities.HiberXUtilities.getGetMethod;
import static utilities.HiberXUtilities.getSetMethod;
import static utilities.StringUtilities.capitalize;
import static utilities.StringUtilities.isJavaKeyword;
import static utilities.StringUtilities.onlyNumbers;
import static utilities.StringUtilities.startsWithNumber;
import static utilities.StringUtilities.verifySpecials;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import handler.SheetHandler;

/**
 * This class is used for creating a bean by using the titles of a table.
 * 
 * @author hugo
 *
 */
public class BeanCreator {

  private static final String endLine = ";\n";
  private static final String tab = "  ";
  private static final String lineSpace = "\n\n";

  private static int progress = 0;


  /**
   * Constructor of the class.
   * 
   * @param xlsx
   *          table (first sheet)
   * @param saida
   *          .java file (bean)
   */
  public void createBean(String xlsx, String saida) {
    progress = 0;
    SheetHandler handler = new SheetHandler();
    File file = new File(xlsx);
    handler.setFile(file);
    handler.setSheet(0);
    try {
      handler.fillTitles(1);
      List<String> titles = handler.getListTitles();
      while (titles.contains(null)) {
        titles.remove(null);
      }
      while (titles.contains("")) {
        titles.remove("");
      }
      if (!validateList(titles)) {
        System.err.println("Títulos inválidos");
        return;
      }
      String name = handler.getFile();
      writeClass(name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf(".")), saida, titles);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }



  /**
   * This method creates the bean
   * 
   * @param name
   *          table (first sheet)
   * @param path
   *          .java file (bean)
   */
  private static void writeClass(String name, String path, List<String> titles) throws IOException {
    File out = new File(path);
    BufferedWriter w = new BufferedWriter(new FileWriter(out));
    w.write(getPackage());
    w.write("\n");
    w.write("public class " + capitalize(name) + " {\n");
    w.write("\n");
    int size = titles.size();

    // Cria as variáveis
    for (String t : titles) {
      progress = (100 * titles.indexOf(t)) / (size * 3);
      w.write(tab + "private String " + t + endLine);
    }
    
    w.write(lineSpace);
    w.write(tab + "/** Private constructor */\n");
    // Cria o constutor privado
    w.write(tab + "private " + capitalize(name) + "{\n");
    w.write(tab + "}\n");

    w.write(lineSpace);
    w.write(tab + "/** Getters */\n");
    // Cria os Getters
    for (String t : titles) {
      progress = 33 + (100 * titles.indexOf(t)) / (size * 3);
      w.write(getMethod(t.trim()));
    }

    w.write(lineSpace);
    w.write(tab + "/** Setters */\n");
    // Cria os Setters
    for (String t : titles) {
      progress = 66 + (100 * titles.indexOf(t)) / (size * 3);
      w.write(setMethod(t.trim()));
    }

    w.write("\n}");

    w.flush();
    w.close();
    progress = 100;
  }


  /**
   * Creates getters
   * 
   * @param t
   * @return
   */
  private static String getMethod(String t) {
    String method = tab + "public String " + getGetMethod(t) + "() {\n";
    method += tab + tab + "return this." + t + endLine;
    method += tab + "}\n";
    return method;
  }


  /**
   * Creates setters.
   * 
   * @param t
   * @return
   */
  private static String setMethod(String t) {
    String method = tab + "public void " + getSetMethod(t) + "(String " + t + ") {\n";
    method += tab + tab + "this." + t + " = " + t + endLine;
    method += tab + "}\n";
    return method;
  }


  /**
   * Creates the package message.
   * 
   * @return
   */
  private static String getPackage() {
    String pack = "";
    pack += "/**\n";
    pack += " * \n";
    pack += " * Remeber to declare the right package of your class in here \n";
    pack += " */\n";
    return pack;
  }


  /**
   * Validates the titles of the xlsx. If they match the java requisitions, return true.
   * 
   * @param titles
   * @return
   */
  private static boolean validateList(List<String> titles) {
    for (String t : titles) {
      if (t != null && !t.isEmpty()) {
        if (verifySpecials(t)) {
          System.err.println("Palavra inválida: " + t + " Contain special characters.");
          return false;
        }
        if (startsWithNumber(t)) {
          System.err.println("Palavra inválida: " + t + " Starts with a number.");
          return false;
        }
        if (onlyNumbers(t)) {
          System.err.println("Palavra inválida: " + t + " Contain only numbers.");
          return false;
        }
        if (isJavaKeyword(t)) {
          System.err.println("Palavra inválida: " + t + " Java keyoword.");
          return false;
        }
      }
      else {
        return true;
      }
    }
    return true;
  }


  public int getProgress() {
    return progress;
  }

}
