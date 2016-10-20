package utilities;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class FileUtilities {

  public static final String FSP = System.getProperty("file.separator");


  /**
   * Private constructor, no one can instantiate this class.
   */
  private FileUtilities() {
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
   * Returns the file length using a {@link DecimalFormat} with the <b>0.00</b> pattern.
   * 
   * @param file
   */
  public static String getFileSizeMB(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFileSizeMB(size);
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
   * Returns the length of the file using a {@link DecimalFormat} with the <b>0.00 KB/MB/GB</b> pattern.
   * 
   * @param file
   * @return
   */
  public static String getFormattedSizeFile(File file) {
    double size = file.length();
    if (file.isDirectory()) {
      size = listFiles(file, true).stream().parallel().mapToLong(File::length).sum();
    }
    return getFormattedSizeFile(size);
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


  /**
   * Returns the MD5 value of the file contained in the <code>path</code>.
   * 
   * @param path
   * @return
   * @throws Exception
   */
  public static String generateMD5(String path) throws Exception {
    return generateMD5(new File(path));
  }


  /**
   * Métodos copiados da classe FileServices.
   */

  /**
   * Returns the MD5 value of the <code>file</code>
   * 
   * @param file
   * @return
   * @throws Exception
   */
  public static String generateMD5(File file) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
    StringBuilder hexFileCSV = new StringBuilder();

    int theByte = 0;
    while ((theByte = in.read()) != -1) {
      // call to the update() method to encrypt the data
      md.update((byte) theByte);
    }
    in.close();

    byte[] theDigest = md.digest();

    for (int i = 0; i < theDigest.length; i++) {
      if ((0xff & theDigest[i]) < 0x10) {
        hexFileCSV.append("0" + Integer.toHexString((0xFF & theDigest[i])));
      }
      else {
        hexFileCSV.append(Integer.toHexString(0xFF & theDigest[i]));
      }
    }
    return hexFileCSV.toString();
  }


  /**
   * Returns this application path.
   * 
   * @param diretorio
   * @return
   */
  public final String getApplicationPath(String diretorio) {
    String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();

    File dir = new File(url).getParentFile();

    String path = dir.getPath();
    String paths[];
    if (FSP.equals("\\")) {
      paths = path.split("\\" + FSP);
    }
    else {
      paths = path.split(FSP);
    }
    if (path.contains(".jar")) {
      path = paths[1];
      for (int i = 2; i < paths.length - 2; i++) {
        path += FSP + paths[i];
      }

    }
    else {
      path = path.replace("classes\\utilities", "");
      path = path.replace("bin\\utilities", "");
      path = path.replaceAll("[\\/]\\s*$", "");
      // path += "\\";
    }
    path += FSP + diretorio;
    path = path.replace("\\\\", "\\");
    try {
      return URLDecoder.decode(path, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      return path.replace("%20", " ");
    }
  }


  /***/
  public static final void createDirectory(String a) throws Exception {
    File f = new File(a);

    if (f.isDirectory()) {
      throw new Exception(String.format("Falha ao criar o diretório '%s'. O diretório já existe!", a));
    }

    if (!f.mkdir()) {
      throw new Exception(String.format("Falha ao criar o diretório '%s'.", a));
    }
  }


  /***/
  public static final void createDirectoryAll(String a) {
    File f = new File(a);
    if (!f.exists())
      f.mkdirs();
  }


  /***/
  public static boolean createDirectoryIgnoreIfExists(String a) {
    File f = new File(a);

    if (f.isDirectory()) {
      return true;
    }

    if (!f.mkdirs()) {
      return false;
    }
    return false;
  }


  /***/
  public static final String escapeFilepath(String a) {
    a = a.replace("\n", "");
    a = a.replace("\\", "");
    a = a.replace("/", "");
    a = a.replace(":", "");
    a = a.replace("*", "");
    a = a.replace("?", "");
    a = a.replace("\"", "");
    a = a.replace("<", "");
    a = a.replace(">", "");
    a = a.replace("|", "");
    a = a.trim();

    return a;
  }


  /***/
  public static final void renameDirectory(String oldName, String newName) throws Exception {
    renameDirectory(new File(oldName), new File(newName));
  }


  /***/
  public static final void renameDirectory(File oldFile, File newFile) throws Exception {
    if (newFile.isDirectory()) {
      throw new Exception("Diretório de destino já existente!");
    }

    if (!oldFile.renameTo(newFile)) {
      throw new Exception("Falha ao renomear o diretório!");
    }
  }


  /***/
  public static final void copyFile(String from, String to) throws Exception {
    copyFile(new File(from), new File(to));
  }


  /***/
  public static final void copyFile(File from, File to) throws Exception {
    if (to.isFile()) {
      throw new Exception(String.format("File '%s' already exists", to.getAbsolutePath()));
    }
    copyFileIgnoreExists(from, to);
  }


  /***/
  public static final void copyFileIgnoreExists(String from, String to) throws Exception {
    copyFileIgnoreExists(new File(from), new File(to));
  }


  /***/
  public static final void copyFileIgnoreExists(File from, File to) throws Exception {
    if (!from.isFile()) {

    }

    InputStream in = new FileInputStream(from);
    OutputStream out = new FileOutputStream(to);

    byte[] buf = new byte[1024];
    int len;

    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }

    in.close();
    out.close();
  }


  /**
   * Deletes the <code>file</code>, or folder.
   * 
   * @param file
   * @return
   */
  public static final boolean delete(File file) {
    if (file.exists()) {
      if (file.isDirectory()) {
        String children[] = file.list();
        for (String child : children) {
          boolean success = delete(new File(file, child));
          if (!success) {
            return false;
          }
        }
      }

    }
    return file.delete();
  }


  /**
   * Returns an {@link LinkedList} containing the files on the <code>diretoryFile</code>.
   * 
   * @param path
   *          {@link File} (directory) to be listed.
   * @param recursive
   *          true if you wish to list all files in sub-folders.
   * @return
   */
  public static final List<File> listFiles(String path, boolean recursive) {
    return listFiles(new File(path), true);
  }


  /**
   * Returns an {@link LinkedList} containing the files on the <code>diretoryFile</code>.
   * 
   * @param directoryFile
   *          {@link File} (directory) to be listed.
   * @param recursive
   *          true if you wish to list all files in sub-folders.
   * @return
   */
  public static final List<File> listFiles(File directoryFile, boolean recursive) {
    List<File> files = new LinkedList<File>();
    String filenames[] = directoryFile.list();
    File file;
    for (String filename : filenames) {
      file = new File(directoryFile, filename);
      if (file.exists() && file.isFile()) {
        files.add(file);
      }
    }
    if (recursive) {
      for (String filename : filenames) {
        file = new File(directoryFile, filename);
        if (file.exists() && file.isDirectory()) {
          files.addAll(listFiles(file, recursive));
        }
      }
    }
    return files;
  }

}
