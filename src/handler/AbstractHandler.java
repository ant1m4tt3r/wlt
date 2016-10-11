package handler;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface AbstractHandler { 
  
  /***/
  public void createSheet(String name);
  
  /***/
  public boolean save(File fileOut);
  
  /***/
  public boolean close(); 
  
  /***/
  public String getFile();
  
  /***/
  public List<String> getListTitles();
  
  public boolean createFile() throws Exception;
  
  /***/
  public boolean setFile(File file) throws Exception;
  
  /***/
  public Integer getLine();
  
  /***/
  public void setLine(Integer line);
  
  /***/
  public boolean fillTitles(int title) throws Exception;
  
  /***/
  public Integer getTitleColum(String pesquisa) throws Exception;
  
  /***/
  public String getValueDate(String pesquisa) throws Exception;
  
  /***/
  public String getCellValue(Integer col, Integer line) throws Exception;
  
  /***/
  public String getCellValue(String pesquisa) throws Exception;
  
  /***/
  public String getCellValueDate(String pesquisa) throws Exception;
  
  /***/
  public String getCellValueDate(int col, int line) throws Exception;
  
  /***/
  public void setCellValue(String pesquisa, String value) throws Exception;
  
  /***/
  public void setCellValue(String pesquisa, Double value) throws Exception;
  
  /***/
  public void setCellValueCientific(String pesquisa, Double value) throws Exception;
  
  /***/
  public void setCellErrorColor(String pesquisa) throws Exception;
  
  /***/
  public void setCellValue(String pesquisa, Integer value) throws Exception;
  
  /***/
  public void setCellValue(String pesquisa, Date date) throws Exception;
  
  /***/
  public void createCellColumn(String pesquisa);

  /***/
  public void setCellValueString(int col, int line, String value);

  /***/
  public boolean setSheet(int aba);
  
  /***/
  public boolean setSheet(String name) throws Exception;
  
  /***/
  public void setCellValueDate(int col, int line, Date value);
  
  /***/
  public void setCellValueDate(int col, int line, String value);
  
  /***/
  public void setCellValueNumeric(int col, int line, int value);
  
  /***/
  public void setCellValueCientific(int col, int line, Double value);
  
  /***/
  public void setCellValue(int col, int line, Double value);
  
  /***/
  public void setCellErrorColor(int col, int line);

  public String getCellValueTime(int col, int line) throws Exception;

  public String getCellValueTime(String pesquisa) throws Exception;

}
