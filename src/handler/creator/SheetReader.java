package handler.creator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import handler.SheetHandler;

public class SheetReader {

  /**
   * 
   * @param Corresponding
   *          class of the bean for the reflection routine
   * @param file
   *          xlsx
   * @return List of beans
   * @throws Exception
   */
  public List<Object> getMultipleResults(Class<?> classe, String file) throws Exception {
    int line = 1;
    int col = 0;
    String value = "";
    List<Object> obj = new ArrayList<Object>(); // Lista de objetos (Beans)
    List<String> data = new ArrayList<String>(); // Armazena os dados correspondentes à cada entrada por linha
    // Leitura do XLSX
    SheetHandler reader = new SheetHandler();
    reader.setFile(new File(file));
    reader.setSheet(0);
    reader.fillTitles(line);
    List<String> titles = reader.getListTitles(); // Armazena os títulos da tabela
    titles.remove(titles.size() - 1); // Remove o último índice (vazio)
    line++;

    while (value != null) { // Percorre as linhas
      for (col = 0; col < titles.size(); col++) { // Percorre as colunas
        data.add(reader.getCellValue(col, line));
      }
      obj.add(HiberX.fill(classe, titles, data)); // Chamada ao método de preeenchimento do bean
      data = new ArrayList<String>();
      col = 0;
      line++;
      value = reader.getCellValue(col, line); // Leitura do próximo valor da tabela.
    }

    return obj;

  }

}
