package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import services.FileServices;

public class FileUtilities {

  public static String getFileSizeMB(double size) {
    DecimalFormat df = new DecimalFormat("0.00");
    float sizeMb = 1024.0f * 1024.0f;
    return df.format(size / sizeMb) + "";
  }


  public static String getFileSizeMB(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = FileServices.listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFileSizeMB(size);
  }


  public static String getFileSizeMB(String pathname) {
    File file = new File(pathname);
    return getFileSizeMB(file);
  }


  public static String getFileLengthWithString(double size) {

    DecimalFormat df = new DecimalFormat("0.00");

    float sizeKb = 1024.0f;
    float sizeMo = sizeKb * sizeKb;
    float sizeGo = sizeMo * sizeKb;
    float sizeTerra = sizeGo * sizeKb;

    if (size < sizeMo)
      return df.format(size / sizeKb) + " KB";
    else if (size < sizeGo)
      return df.format(size / sizeMo) + " MB";
    else if (size < sizeTerra)
      return df.format(size / sizeGo) + " GB";

    return "";
  }


  public static String getStringSizeLengthFile(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = FileServices.listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFileLengthWithString(size);
  }


  public static String getStringSizeLengthFile(String pathname) {
    File file = new File(pathname);
    return getStringSizeLengthFile(file);
  }


  public static void runFile(String file) throws IOException {
    runFile(new File(file));
  }


  private static void runFile(File file) throws IOException {
    Desktop.getDesktop().open(file);
  }

}
