package utilities;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import services.FileServices;

public class FileUtilities {

  /**
   * Private constructor, no one can instantiate this class.
   */
  private FileUtilities() {
  }


  /**
   * Returns the size using a {@link DecimalFormat} with the <b>0.00</b> pattern.
   * 
   * @param size
   */
  public static String getFileSizeMB(double size) {
    DecimalFormat df = new DecimalFormat("0.00");
    float sizeMb = 1024.0f * 1024.0f;
    return df.format(size / sizeMb) + "";
  }


  /**
   * Returns the file length using a {@link DecimalFormat} with the <b>0.00</b> pattern.
   * 
   * @param file
   */
  public static String getFileSizeMB(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = FileServices.listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFileSizeMB(size);
  }


  /**
   * Returns the length of the file in the <code>pathname</code> using a {@link DecimalFormat} with the <b>0.00</b> pattern.
   * 
   * @param pathname
   */
  public static String getFileSizeMB(String pathname) {
    File file = new File(pathname);
    return getFileSizeMB(file);
  }


  /**
   * Returns the size using a {@link DecimalFormat} with the <b>0.00 KB/MB/GB</b> pattern.
   * 
   * @param size
   * @return
   */
  public static String getFormattedSizeFile(double size) {
    DecimalFormat df = new DecimalFormat("0.00");

    float sizeKb = 1024.0f;
    float sizeMb = sizeKb * sizeKb;
    float sizeGb = sizeMb * sizeKb;
    float sizeTb = sizeGb * sizeKb;

    if (size < sizeMb)
      return df.format(size / sizeKb) + " KB";
    else if (size < sizeGb)
      return df.format(size / sizeMb) + " MB";
    else if (size < sizeTb)
      return df.format(size / sizeGb) + " GB";

    return "";
  }


  /**
   * Returns the length of the file using a {@link DecimalFormat} with the <b>0.00 KB/MB/GB</b> pattern.
   * 
   * @param file
   * @return
   */
  public static String getFormattedSizeFile(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = FileServices.listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFormattedSizeFile(size);
  }


  /**
   * Returns the length of the file contained in the <code>pathname</code> using a {@link DecimalFormat} with the <b>0.00 KB/MB/GB</b> pattern.
   * 
   * @param pathname
   * @return
   */
  public static String getFormattedSizeFile(String pathname) {
    File file = new File(pathname);
    return getFormattedSizeFile(file);
  }


  /**
   * Runs the file using the default program of your computer.
   * 
   * @param path
   *          a {@link String} containing the path of the file.
   * @throws IOException
   */
  public static void runFile(String path) throws IOException {
    runFile(new File(path));
  }


  /**
   * Runs the <code>file<code> using the default program of your computer.
   * 
   * @param file
   *          a {@link File} to be executed.
   * @throws IOException
   */
  public static void runFile(File file) throws IOException {
    Desktop.getDesktop().open(file);
  }


  /**
   * Takes a screenshot and saves it on the <code>path</code>. The default extension is PNG.
   * 
   * @param path
   *          Output file.
   * @throws IOException
   * @throws AWTException
   */
  public static void printScreen(String path) throws IOException, AWTException {
    printScreen(path, "PNG");
  }


  /**
   * Takes a screenshot and saves it on the <code>path</code> with the determined <code>extension</code>.
   * 
   * @param path
   *          output file.
   * @param extension
   *          image extension.
   * @throws IOException
   * @throws AWTException
   */
  public static void printScreen(String path, String extension) throws IOException, AWTException {
    Robot r = new Robot();
    Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    BufferedImage i = r.createScreenCapture(rect);
    ImageIO.write(i, extension, new File(path));
  }

}
