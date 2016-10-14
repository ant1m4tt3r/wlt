package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FileServices {

  public static final String FSP = System.getProperty("file.separator");
  private static final FileServices singleton = new FileServices();
  private static Map<String, String> mimeExtensionMap = null;
  private static Random random = new Random();


  /** return static reference to singleton pattern object */
  public static FileServices getInstance() {
    return singleton;
  }


  /***/
  private FileServices() {
    super();
  }


  public static String generateMD5(String file) throws Exception {
    return generateMD5(new File(file));
  }


  public static String generateMD5(File file) throws Exception {

    MessageDigest md = MessageDigest.getInstance("MD5");
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
    StringBuilder hexFileCSV = new StringBuilder();

    int theByte = 0;
    while ((theByte = in.read()) != -1) {
      // chamar o m�todo update() para passar os dados a serem criptografados
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


  /***/
  public final String getApplicationPath(String diretorio) {
    String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();

    File dir = new File(url).getParentFile();

    String path = dir.getPath();
    String paths[];
    if (FileServices.FSP.equals("\\")) {
      paths = path.split("\\" + FileServices.FSP);
    }
    else {
      paths = path.split(FileServices.FSP);
    }
    if (path.contains(".jar")) {
      path = paths[1];
      for (int i = 2; i < paths.length - 2; i++) {
        path += FileServices.FSP + paths[i];
      }

    }
    else {
      path = path.replace("classes\\utilities", "");
      path = path.replace("bin\\utilities", "");
      path = path.replaceAll("[\\/]\\s*$", "");
      // path += "\\";
    }
    path += FileServices.FSP + diretorio;

    path = path.replace("\\\\", "\\");
    // System.out.println("--> " + path);

    try {
      return URLDecoder.decode(path, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      return path.replace("%20", " ");
    }
  }


  /***/
  public static final boolean directoryExists(String a) throws Exception {
    File f = new File(a);

    return f.isDirectory();
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
  public static final void createDirectoryAll(String a) throws Exception {

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
      throw new Exception("Falha ao renomear o diret�rio!");
    }
  }


  /***/
  public static final File suggestDirectory(String a) {
    File f = new File(a);

    int i = 2;

    while (f.isDirectory()) {
      f = new File(String.format("%s(%d)", a, i++));
    }

    return f;
  }


  /**
   * path represent a base directory. a represent filename with a pattern (%s) which will be replaced at first time to empty string and, once time
   * this represent a real file, it will be replace to a number increasing starting at 1 until file not exist.
   */
  public static final File suggestFile(String a) {
    if (!a.contains("%s")) {
      int pointIndex = a.lastIndexOf('.');

      if (pointIndex == -1) {
        pointIndex = a.length();
      }

      a = a.substring(0, pointIndex) + "%s" + a.substring(pointIndex);
    }

    File file = new File(a.replaceAll("%s", ""));

    int i = 1;

    while (file.isFile()) {
      file = new File(a.replaceAll("%s", String.format("(%d)", i++)));
    }

    return file;
  }


  /***/
  public static final void copyDir(File from, File to) throws Exception {
    if (from.isFile() || to.isFile() || from.equals(to)) {
      throw new Exception(String.format("Diretórios inválidos!"));
    }

    if (!to.isDirectory()) {
      if (!to.mkdirs()) {
        throw new Exception(String.format("Falha ao criar o diretório '%s'!", to.getAbsoluteFile()));
      }
    }

    String fromDir = from.getAbsolutePath();
    String toDir = to.getAbsolutePath();

    String ls[] = from.list();

    File newFrom, newTo;

    for (String l : ls) {
      newFrom = new File(fromDir + "\\" + l);
      newTo = new File(toDir + "\\" + l);

      if (newFrom.isFile()) {
        copyFile(newFrom, newTo);
      }
      else {
        if (newFrom.isDirectory()) {
          copyDir(newFrom, newTo);
        }
      }
    }
  }


  public static final void copyDirIgnore(File from, File to) throws Exception {
    if (from.isFile() || to.isFile() || from.equals(to)) {
      throw new Exception(String.format("Diretórios inválidos!"));
    }

    if (!to.isDirectory()) {
      if (!to.mkdirs()) {
        throw new Exception(String.format("Falha ao criar o diretório '%s'!", to.getAbsoluteFile()));
      }
    }

    String fromDir = from.getAbsolutePath();
    String toDir = to.getAbsolutePath();

    String ls[] = from.list();

    File newFrom, newTo;

    for (String l : ls) {
      newFrom = new File(fromDir + "\\" + l);
      newTo = new File(toDir + "\\" + l);

      if (newFrom.isFile()) {
        copyFileIgnoreExists(newFrom, newTo);
      }
      else {
        if (newFrom.isDirectory()) {
          copyDir(newFrom, newTo);
        }
      }
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


  /***/
  public static final boolean delete(File file) throws Exception {
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


  /***/
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


  /***/
  public static final List<File> listFilesExtencao(File directoryFile, boolean recursive, String extencao) {
    List<File> files = new LinkedList<File>();
    String ext[] = extencao.split(",");
    String filenames[] = directoryFile.list();
    File file;

    for (String filename : filenames) {
      file = new File(directoryFile, filename);

      for (String s : ext) {
        if (file.exists() && file.isFile() && file.getName().toLowerCase().endsWith(s)) {
          files.add(file);
        }
      }
    }

    if (recursive) {
      for (String filename : filenames) {
        file = new File(directoryFile, filename);

        if (file.exists() && file.isDirectory()) {
          files.addAll(listFilesExtencao(file, recursive, extencao));
        }
      }
    }

    return files;
  }


  /***/
  public static final HashMap<String, File> HashFilesExtencao(File directoryFile, boolean recursive) throws Exception {
    HashMap<String, File> files = new HashMap<String, File>();

    String filenames[] = directoryFile.list();
    File file;

    for (String filename : filenames) {
      file = new File(directoryFile, filename);

      if (file.exists() && file.isFile()) {
        files.put(file.getName(), file);
      }
    }

    if (recursive) {
      for (String filename : filenames) {
        file = new File(directoryFile, filename);

        if (file.exists() && file.isDirectory()) {
          files.putAll(HashFilesExtencao(file, recursive));
        }
      }
    }

    return files;
  }


  /***/
  public static final List<File> listDirectory(File directoryFile, boolean recursive) throws Exception {
    List<File> files = new ArrayList<File>();

    String filenames[] = directoryFile.list();
    File file;

    for (String filename : filenames) {
      file = new File(directoryFile, filename);

      if (file.exists() && file.isDirectory()) {
        files.add(file);
      }
    }

    if (recursive) {
      for (String filename : filenames) {
        file = new File(directoryFile, filename);

        if (file.exists() && file.isDirectory()) {
          files.addAll(listDirectory(file, recursive));
        }
      }
    }

    return files;
  }


  /***/
  public static String getAssocFileUuid(String filename) {
    if (random == null) {
      random = new Random();
    }

    random.setSeed(filename.hashCode());

    return String.format("%s-%s-%s-%s-%s", nextHexBlock(8), nextHexBlock(4), nextHexBlock(4), nextHexBlock(4), nextHexBlock(12));
  }


  /***/
  private static String nextHexBlock(int blockSize) {
    StringBuilder uuid = new StringBuilder();

    for (int i = 0; i < blockSize; ++i) {
      int number = Math.abs(random.nextInt()) % 16;

      if (number < 10) {
        uuid.append(Integer.toString(number));
      }
      else {
        uuid.append(Character.toString((char) (55 + number)));
      }
    }

    return uuid.toString();
  }


  private static final void initializeAllowedExtension() {
    if (mimeExtensionMap == null) {
      mimeExtensionMap = new HashMap<String, String>();

      mimeExtensionMap.put("avi", "application/x-authorware-bin");
      mimeExtensionMap.put("bak", "application/x-authorware-bin");
      mimeExtensionMap.put("bat", "application/x-authorware-bin");
      mimeExtensionMap.put("bmp", "image/bmp");
      mimeExtensionMap.put("bmr", "application/x-authorware-bin");
      mimeExtensionMap.put("cdr", "application/x-cdr");
      mimeExtensionMap.put("cfg", "application/x-authorware-bin");
      mimeExtensionMap.put("crp", "application/x-authorware-bin");
      mimeExtensionMap.put("dat", "multipart/form-data");
      mimeExtensionMap.put("doc", "application/msword");
      mimeExtensionMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
      mimeExtensionMap.put("dsf", "application/x-authorware-bin");
      mimeExtensionMap.put("dt", "application/xml-dtd");
      mimeExtensionMap.put("dt1", "application/x-authorware-bin");
      mimeExtensionMap.put("dwg", "application/acad");
      mimeExtensionMap.put("dzt", "application/x-authorware-bin");
      mimeExtensionMap.put("emf", "application/x-authorware-bin");
      mimeExtensionMap.put("eve", "application/x-authorware-bin");
      mimeExtensionMap.put("hd", "application/x-authorware-bin");
      mimeExtensionMap.put("inv", "application/x-authorware-bin");
      mimeExtensionMap.put("jpg", "image/jpeg");
      mimeExtensionMap.put("las", "application/x-shockwave-flash");
      mimeExtensionMap.put("log", "text/plain");
      mimeExtensionMap.put("mis", "application/x-authorware-bin");
      mimeExtensionMap.put("mst", "application/x-authorware-bin");
      mimeExtensionMap.put("pcx", "application/x-authorware-bin");
      mimeExtensionMap.put("pdf", "application/pdf");
      mimeExtensionMap.put("png", "image/png");
      mimeExtensionMap.put("ppt", "application/vnd.ms-powerpoint");
      mimeExtensionMap.put("pqo", "application/x-authorware-bin");
      mimeExtensionMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
      mimeExtensionMap.put("rad", "application/x-authorware-bin");
      mimeExtensionMap.put("rd3", "application/x-authorware-bin");
      mimeExtensionMap.put("s4k", "application/x-authorware-bin");
      mimeExtensionMap.put("sgy", "application/x-authorware-bin");
      mimeExtensionMap.put("svy", "application/x-authorware-bin");
      mimeExtensionMap.put("sxw", "application/x-authorware-bin");
      mimeExtensionMap.put("txt", "text/plain");
      mimeExtensionMap.put("tcd", "application/x-authorware-bin");
      mimeExtensionMap.put("tif", "image/tif");
      mimeExtensionMap.put("xls", "application/vnd.ms-excel");
      mimeExtensionMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      mimeExtensionMap.put("zon", "application/x-authorware-bin");
      mimeExtensionMap.put("zip", "application/x-zip-compressed");
      mimeExtensionMap.put("xml", "text/xml");
    }
  }


  public static final String extractExtension(String filename) throws Exception {
    initializeAllowedExtension();

    int index = filename.lastIndexOf(".");

    if (index == -1) {
      throw new Exception(String.format("Extensão não encontrada para o arquivo %s", filename));
    }

    String extension = filename.substring(index + 1);

    return extension;
  }


  public static final String extractMime(String filename) throws Exception {
    initializeAllowedExtension();

    String extension = extractExtension(filename);

    return mimeExtensionMap.get(extension);
  }

}
