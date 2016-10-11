package utilities;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.IndexedColors;

import components.progress.Progress;
import components.progress.ProgressDialog;
import components.table.JTableAlternated;
import constants.ErrorConstants;
import handler.SheetHandler;

public class JTableUtilities implements Progress {
  public static final String ACTION_FILTER = "Filtro";
  private String progressTitle;
  private int percentComplete;


  public JTableUtilities(String title) {
    this.progressTitle = title;
  }


  public static void putTableInPanel(JPanel panelTable, JTableAlternated table, String actionCommand) {

    panelTable.setBorder(BorderFactory.createTitledBorder(actionCommand));
    panelTable.removeAll();
    panelTable.setLayout(new GridBagLayout());

    table.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());
    JScrollPane js = new JScrollPane(table);

    GridBagConstraints cons = new GridBagConstraints();
    cons.gridx = 0;
    cons.gridy = 0;
    cons.weightx = 0.5;
    int inset = 10;
    cons.insets = new Insets(inset, inset, inset, inset);
    cons.fill = GridBagConstraints.BOTH;
    cons.weighty = 1;
    cons.weightx = 1;
    cons.gridy = 1;
    panelTable.add(js, cons);

  }


  public boolean saveFileToExcel(String selectedFile, String title, JTable table) {

    if (!selectedFile.endsWith(".xlsx"))
      selectedFile += ".xlsx";
    SheetHandler writer = new SheetHandler();
    writer.createFile();
    writer.createSheet(title);
    int total = table.getColumnCount() * table.getRowCount();
    int cont = 0;
    for (int column = 0; column < table.getColumnCount(); column++) {
      writer.setCellValueString(column, 1, table.getTableHeader().getColumnModel().getColumn(column).getHeaderValue().toString());
      writer.setCellColor(column, 1, IndexedColors.AQUA);
      for (int line = 0; line < table.getRowCount(); line++) {
        String value = table.getValueAt(line, column) == null ? null : table.getValueAt(line, column).toString();
        Double dValue = Utilities.parseToDouble(value);
        if (dValue != null && !dValue.equals(ErrorConstants.ERROR_DOUBLE)) {
          writer.setCellValueCientific(column, line + 2, dValue);

        }
        else {
          writer.setCellValueString(column, line + 2, value);
        }
        cont++;
        percentComplete = cont * 100 / total;
        if(percentComplete == 100)
          progressTitle = "Download completo!";
      }
    }
    if (writer.save(new File(selectedFile))) {
      return true;
    }

    return false;
  }


  public void downloadExcel(String title, JTable table) {
    List<Progress> list = new ArrayList<Progress>();
    list.add(this);
    if (title == null) {
      JOptionPane.showMessageDialog(null, "Dados não encontrados para fazer download");
      return;
    }
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (xlsx)", "xlsx");
    chooser.addChoosableFileFilter(filter);
    chooser.setFileFilter(filter);
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    if (chooser.showSaveDialog(null) == JFileChooser.FILES_ONLY) {
      new Thread(new Runnable() {

        @Override
        public void run() {
          saveFileToExcel(chooser.getSelectedFile().toString(), title, table);

        }
      }).start();
    }
    try {
      TimeUnit.SECONDS.sleep(1);
    }
    catch (InterruptedException e) {
    }
    new ProgressDialog("Fazendo download, aguarde...", list);
  }


  @Override
  public int getProgress() {
    return percentComplete;
  }


  @Override
  public String getProgressName() {

    return progressTitle;
  }
}
