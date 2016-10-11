package handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilities.StringUtilities;

public class SheetHandler implements AbstractHandler {

  private FileOutputStream output = null;
  public XSSFWorkbook book = null;
  private XSSFSheet sheet = null;
  private XSSFRow row = null;
  private XSSFCell cell = null;
  private CellStyle style = null;
  private CellStyle styleDouble = null;
  private CellStyle styleCollor = null;
  private FileInputStream fileIn = null;
  private String file;

  List<String> listTitles = null;
  private Integer line = null;
  private boolean keepFormat = false;


  public SheetHandler() {
  }


  @Override
  public void createSheet(String name) {
    sheet = book.createSheet(name);

  }


  public int getSheetSize() {
    int index = 0;
    String s = null;

    do {
      s = book.getSheetName(0);
      index++;
    } while (s != null);

    return index;
  }


  @Override
  public boolean save(File fileOut) {
    try {
      this.output = new FileOutputStream(fileOut);
      this.book.write(this.output);
      this.output.close();
      if (fileIn != null)
        fileIn.close();
      row = null;
      cell = null;
      sheet = null;
      book = null;
      return true;

    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }


  @Override
  public boolean close() {
    try {
      fileIn.close();
      output = null;
      book = null;
      sheet = null;
      row = null;
      cell = null;
      style = null;
      styleCollor = null;
      file = null;
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }


  @Override
  public String getFile() {
    return file;
  }


  @Override
  public boolean createFile() {
    return setFile(null);
  }


  @Override
  public boolean setFile(File file) {
    try {
      if (file != null) {
        this.file = file.getAbsolutePath();
        this.fileIn = new FileInputStream(file);
        this.book = new XSSFWorkbook(this.fileIn);
      }
      else {
        this.book = new XSSFWorkbook();
      }
      style = book.createCellStyle();
      style.setFillForegroundColor((short) 9);
      style.setFillBackgroundColor((short) 9);
      style.setBorderTop(CellStyle.BORDER_THIN);
      style.setBorderLeft(CellStyle.BORDER_THIN);
      style.setBorderRight(CellStyle.BORDER_THIN);
      style.setBorderBottom(CellStyle.BORDER_THIN);

      styleDouble = book.createCellStyle();
      styleDouble.setFillForegroundColor((short) 9);
      styleDouble.setFillBackgroundColor((short) 9);
      styleDouble.setBorderTop(CellStyle.BORDER_THIN);
      styleDouble.setBorderLeft(CellStyle.BORDER_THIN);
      styleDouble.setBorderRight(CellStyle.BORDER_THIN);
      styleDouble.setBorderBottom(CellStyle.BORDER_THIN);

      styleCollor = book.createCellStyle();
      styleCollor.setFillForegroundColor((short) 10);
      styleCollor.setFillBackgroundColor((short) 10);
      styleCollor.setFillPattern(CellStyle.DIAMONDS);
      styleCollor.setBorderTop(CellStyle.BORDER_THIN);
      styleCollor.setBorderLeft(CellStyle.BORDER_THIN);
      styleCollor.setBorderRight(CellStyle.BORDER_THIN);
      styleCollor.setBorderBottom(CellStyle.BORDER_THIN);
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;

    }

  }


  public void unmerge(String name) throws Exception {
    setSheet(name);
    unmerge();
  }


  public void unmerge(int sheetIndex) throws Exception {
    setSheet(sheetIndex);
    unmerge();
  }


  public void unmerge() throws Exception {
    Set<Integer> regionsToRemove = new HashSet<Integer>();
    boolean oldKeepFormat = keepFormat;
    for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
      CellRangeAddress n = sheet.getMergedRegion(i);
      regionsToRemove.add(i);
      for (int col = n.getFirstColumn(); col <= n.getLastColumn(); col++) {
        for (int line = n.getFirstRow(); line <= n.getLastRow(); line++) {
          keepFormat = true;
          setCellValueString(col, line + 1, getCellValue(n.getFirstColumn(), n.getFirstRow() + 1));
          keepFormat = oldKeepFormat;
        }
      }
    }

    // sheet.removeMergedRegions(regionsToRemove);

  }


  @Override
  public Integer getLine() {
    return line;
  }


  @Override
  public void setLine(Integer line) {
    this.line = line;
  }


  public String verifyTitles(List<String> titles) {
    String msgResult = "";
    for (String title : titles) {
      String aux = StringUtilities.formatStringLowerNoSpace(title);
      if (!listTitles.contains(aux)) {
        msgResult = msgResult.isEmpty() ? msgResult + title : msgResult + ", " + title;
      }
    }
    if (msgResult.contains(",")) {
      StringBuilder b = new StringBuilder(msgResult);
      b.replace(msgResult.lastIndexOf(","), msgResult.lastIndexOf(",") + 1, " e ");
      msgResult = b.toString();
    }
    return msgResult;
  }


  /**
   * @param usada
   *          para preencher os titulos da coluna
   */
  @Override
  public boolean fillTitles(int title) throws Exception {
    listTitles = new ArrayList<String>();
    listTitles.clear();
    String value = " ";
    int col = 0;
    Integer cont = 0;
    while (value != null && cont < 2) {
      value = StringUtilities.formatStringLowerNoSpace(getCellValue(col, title));

      if (value == null || value == "") {
        cont++;
        value = "";
      }
      else {
        cont = 0;
      }
      listTitles.add(value);
      col++;
    }
    listTitles.remove(listTitles.size() - 1);
    return true;
  }


  /** usada para retornar a coluna de acordo com o titulo setado anteriormente */
  @Override
  public Integer getTitleColum(String pesquisa) throws Exception {

    int max = listTitles.size();

    for (int i = 0; i < max; i++) {
      if (listTitles.get(i).equals(pesquisa)) {
        return i;
      }
    }
    throw new Exception("Não foi encontrado o titulo '" + pesquisa + "' na aba: '" + sheet.getSheetName() + "' em: " + file.substring(file.lastIndexOf("\\") + 1));

  }


  @Override
  public String getValueDate(String pesquisa) throws Exception {
    int col = getTitleColum(pesquisa);
    return getCellValueDate(col, line);
  }


  /**
   * @author kadu returns a string with the cell value
   */
  @Override
  public String getCellValue(Integer col, Integer line) throws Exception {
    if (col == null || line == null) {
      throw new Exception(String.format("Coluna ou linha inválida!", col, line));
    }
    if (sheet == null)
      throw new Exception("Aba não setada");
    try {
      line = line - 1;
      this.row = sheet.getRow(line);
      if (!(row == null)) {
        this.cell = row.getCell(col);
        if (!(cell == null)) {
          if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue() + "";
          }
          if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue() + "";
          }

          if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            String result = null;
            switch (cellValue.getCellType()) {
              case Cell.CELL_TYPE_BOOLEAN:
                result = cellValue.getBooleanValue() + "";
                break;

              case Cell.CELL_TYPE_NUMERIC:
                result = cellValue.getNumberValue() + "";
                break;
              case Cell.CELL_TYPE_STRING:
                result = cellValue.getStringValue();
                break;
            }

            if (result != null && result.isEmpty()) {
              result = null;
            }
            return result;
          }

          this.cell.setCellType(Cell.CELL_TYPE_STRING);

          String result = cell.getStringCellValue() + "";

          if (result != null && result.isEmpty()) {
            result = null;
          }
          return result;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new Exception(String.format("Não foi possível fazer a leitura da célula de coluna: %s e linha: %s no arquivo: %s", col, line + 1, file.substring(file.lastIndexOf("\\") + 1)));
    }
    return null;
  }


  public String getCellValueString(Integer col, Integer line) throws Exception {
    if (col == null || line == null) {
      throw new Exception(String.format("Coluna ou linha inválida!", col, line));
    }
    try {
      line = line - 1;
      this.row = sheet.getRow(line);
      if (!(row == null)) {
        this.cell = row.getCell(col);
        if (!(cell == null)) {

          if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue() + "";
          }
          else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue() + "";
          }

          String result = cell.getStringCellValue() + "";

          if (result != null && result.isEmpty()) {
            result = null;
          }
          return result;
        }
      }
    }
    catch (Exception e) {
      throw new Exception(String.format("Não foi possível fazer a leitura da célula de coluna: %s e linha: %s e no arquivo: %s", col, line + 1, file.substring(file.lastIndexOf("\\") + 1)));
    }
    return null;
  }


  @Override
  public String getCellValue(String pesquisa) throws Exception {
    int colum = getTitleColum(pesquisa);
    String aux = getCellValue(colum, line);
    return StringUtilities.parseToString(aux);
  }


  @Override
  public String getCellValueDate(String pesquisa) throws Exception {
    int colum = getTitleColum(pesquisa);
    return getCellValueDate(colum, line);
  }


  @Override
  public String getCellValueTime(String pesquisa) throws Exception {
    int colum = getTitleColum(pesquisa);
    return getCellValueTime(colum, line);
  }


  @Override
  public String getCellValueDate(int col, int line) throws Exception {
    Date d = null;
    line = line - 1;
    row = sheet.getRow(line);
    if (!(row == null)) {
      cell = row.getCell(col);
      if ((cell != null) && (!(cell.toString().equals("")))) {
        String aux = null;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy k:mm");
        switch (cell.getCellType()) {
          case Cell.CELL_TYPE_NUMERIC:
            d = cell.getDateCellValue();
            break;

          case Cell.CELL_TYPE_STRING:
            aux = cell.getStringCellValue();
            aux = aux.replace("24:", "0:");
            return aux;
          case Cell.CELL_TYPE_FORMULA:

            FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
              case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                d = cell.getDateCellValue();
                break;
              case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
              default:
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                d = cell.getDateCellValue();
                break;
            }
            break;

        }
        if (d != null) {
          aux = f.format(d);
          aux = aux.replace("24:", "0:");
        }
        return aux;
      }
    }
    return null;
  }


  @Override
  public String getCellValueTime(int col, int line) throws Exception {
    Date d = null;
    line = line - 1;
    row = sheet.getRow(line);
    if (!(row == null)) {
      cell = row.getCell(col);
      if ((cell != null) && (!(cell.toString().equals("")))) {
        String aux = null;
        SimpleDateFormat f = new SimpleDateFormat("k:mm");
        switch (cell.getCellType()) {
          case Cell.CELL_TYPE_NUMERIC:
            d = cell.getDateCellValue();
            break;

          case Cell.CELL_TYPE_STRING:
            aux = cell.getStringCellValue();
            aux = aux.replace("24:", "0:");
            return aux;
          case Cell.CELL_TYPE_FORMULA:

            FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
              case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                d = cell.getDateCellValue();
                break;
              case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
              default:
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                d = cell.getDateCellValue();
                break;
            }
            break;

        }
        if (d != null) {
          aux = f.format(d);
          aux = aux.replace("24:", "0:");
        }
        return aux;
      }
    }
    return null;
  }


  /**
   * @author cadu
   * @param pesquisa
   *          nome da coluna a ser inserido o dado
   * @param value
   *          valor a ser inserido na celula
   */
  @Override
  public void setCellValue(String pesquisa, String value) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellValueString(colum, line, value);
  }


  @Override
  public void setCellValue(String pesquisa, Double value) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellValue(colum, line, value);
  }


  @Override
  public void setCellValueCientific(String pesquisa, Double value) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellValueCientific(colum, line, value);
  }


  /**
   * @author cadu
   * @param pesquisa
   *          nome da coluna a ser inserido o dado
   * @param value
   *          valor a ser inserido na celula
   */
  @Override
  public void setCellErrorColor(String pesquisa) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellErrorColor(colum, line);
  }


  public void setCellColor(String pesquisa, IndexedColors color) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellColor(colum, line, color);
  }


  /**
   * @author cadu
   * @param pesquisa
   *          nome da coluna a ser inserido o dado
   * @param value
   *          valor a ser inserido na celula
   */
  @Override
  public void setCellValue(String pesquisa, Integer value) throws Exception {
    int colum = getTitleColum(pesquisa);
    setCellValueNumeric(colum, line, value);
  }


  /**
   * @author cadu
   * @param pesquisa
   *          nome da coluna a ser inserido o dado
   * @param value
   *          valor a ser inserido na celula
   */
  @Override
  public void setCellValue(String pesquisa, Date date) throws Exception {
    String value = StringUtilities.dateToString(date);
    int colum = getTitleColum(pesquisa);
    setCellValueString(colum, line, value);
  }


  @Override
  public void createCellColumn(String pesquisa) {
    // row = sheet.getRow(1);
    // cell = row.createCell(1);
    cell.setCellValue(pesquisa);
  }


  @Override
  public void setCellValueString(int col, int line, String value) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    if (!keepFormat)
      cell.setCellStyle(style);
    else
      cell.setCellStyle(cell.getCellStyle());
    if ((value != null) && (!value.equals("null"))) {
      cell.setCellValue(value);
    }

  }


  // public void newSetCellValueString(int col, int line, String value) {
  // line = line - 1;
  // if (sheet.getRow(line) == null)
  // row = sheet.createRow(line);
  // else
  // row = sheet.getRow(line);
  // if (row.getCell(col) == null)
  // cell = row.createCell(col);
  // else
  // cell = row.getCell(col);
  //
  // if ((value != null) && (!value.equals("null"))) {
  // cell.setCellValue(value);
  // }
  //
  // }

  @Override
  public boolean setSheet(int aba) {
    try {
      sheet = book.getSheetAt(aba);
      if (sheet == null) {
        return false;
      }
      else {
        return true;
      }
    }
    catch (Exception e) {
      return false;
    }
  }


  @Override
  public boolean setSheet(String name) throws Exception {
    try {
      this.sheet = book.getSheet(name);
      if (sheet == null) {
        throw new Exception("Aba " + name + " não encontrada no arquivo " + file);
      }
    }
    catch (Exception e) {
      throw new Exception("Aba " + name + " não encontrada no arquivo " + file);
    }
    return true;
  }


  @Override
  public void setCellValueDate(int col, int line, Date value) {
    if (value == null) {
      return;
    }
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);

    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    cell.setCellValue(value);
    cell.setCellStyle(style);
  }


  @Override
  public void setCellValueDate(int col, int line, String value) {
    Date d = null;
    if (value == null || value.isEmpty())
      return;
    try {
      DateFormat f = new SimpleDateFormat("dd/MM/yyyy k:mm");
      d = f.parse(value);
    }
    catch (Exception e) {
      e.printStackTrace();

    }
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    cell.setCellValue(d);
    cell.setCellStyle(style);
    cell.setCellType(Cell.CELL_TYPE_NUMERIC);

  }


  @Override
  public void setCellValueNumeric(int col, int line, int value) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    if (cell != null)
      cell.setCellValue(value);
    if (!keepFormat)
      cell.setCellStyle(style);
  }


  @Override
  public void setCellValueCientific(int col, int line, Double value) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    styleDouble.setDataFormat(cell.getCellStyle().getDataFormat());

    if (cell != null && value != null) {
      cell.setCellValue(value);
    }
    if (!keepFormat)
      // cell.setCellStyle(style);
      cell.setCellStyle(styleDouble);
    else
      cell.setCellStyle(cell.getCellStyle());
  }


  // public void setCellValueCientific(int col, int line, BigDecimal value) {
  // line = line - 1;
  // if (sheet.getRow(line) == null)
  // row = sheet.createRow(line);
  // else
  // row = sheet.getRow(line);
  // if (row.getCell(col) == null)
  // cell = row.createCell(col);
  // else
  // cell = row.getCell(col);
  // styleDouble.setDataFormat(cell.getCellStyle().getDataFormat());
  //
  // if (cell != null && value != null) {
  // cell.setCellValue(value.doubleValue());
  // }
  // cell.setCellStyle(styleDouble);
  // }

  @Override
  public void setCellValue(int col, int line, Double value) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    // style.setDataFormat(cell.getCellStyle().getDataFormat());
    cell.setCellType(Cell.CELL_TYPE_NUMERIC);

    if (cell != null)
      cell.setCellValue(value);
    cell.setCellStyle(style);
  }


  @Override
  public void setCellErrorColor(int col, int line) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);

    if (!keepFormat) {
      styleCollor = book.createCellStyle();
      styleCollor.setBorderTop(CellStyle.BORDER_THIN);
      styleCollor.setBorderRight(CellStyle.BORDER_THIN);
      styleCollor.setBorderBottom(CellStyle.BORDER_THIN);
      styleCollor.setBorderLeft(CellStyle.BORDER_THIN);
      styleCollor.setFillPattern(CellStyle.SOLID_FOREGROUND);
      styleCollor.setFillBackgroundColor(IndexedColors.RED.getIndex());
      styleCollor.setFillForegroundColor(IndexedColors.RED.getIndex());

    }
    else {
      styleCollor.setBorderTop(cell.getCellStyle().getBorderTop());
      styleCollor.setBorderRight(cell.getCellStyle().getBorderRight());
      styleCollor.setBorderBottom(cell.getCellStyle().getBorderBottom());
      styleCollor.setBorderLeft(cell.getCellStyle().getBorderLeft());
      styleCollor.setDataFormat(cell.getCellStyle().getDataFormat());
      styleCollor.setFillPattern(CellStyle.SOLID_FOREGROUND);
      styleCollor.setFillBackgroundColor(IndexedColors.RED.getIndex());
      styleCollor.setFillForegroundColor(IndexedColors.RED.getIndex());
    }
    cell.setCellStyle(styleCollor);

  }


  public void setCellColor(int col, int line, IndexedColors color) {
    line = line - 1;
    if (sheet.getRow(line) == null)
      row = sheet.createRow(line);
    else
      row = sheet.getRow(line);
    if (row.getCell(col) == null)
      cell = row.createCell(col);
    else
      cell = row.getCell(col);
    styleCollor = book.createCellStyle();
    styleCollor.setDataFormat(cell.getCellStyle().getDataFormat());
    styleCollor.setFillPattern(CellStyle.DIAMONDS);
    styleCollor.setBorderTop(CellStyle.BORDER_THIN);
    styleCollor.setBorderRight(CellStyle.BORDER_THIN);
    styleCollor.setBorderBottom(CellStyle.BORDER_THIN);
    styleCollor.setBorderLeft(CellStyle.BORDER_THIN);
    styleCollor.setFillBackgroundColor(color.getIndex());
    styleCollor.setFillForegroundColor(color.getIndex());
    cell.setCellStyle(styleCollor);
  }


  public boolean isKeepFormat() {
    return keepFormat;
  }


  public void setKeepFormat(boolean keepFormat) {
    this.keepFormat = keepFormat;
  }


  @Override
  public List<String> getListTitles() {
    return this.listTitles;
  }

}
