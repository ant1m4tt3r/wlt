package components.table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

/**
 * Class used to alternate a tables color and used to block some lines that doen't match the rule
 */
public class JTableAlternated extends JXTable {
  private static final long serialVersionUID = 6731232329165094728L;
  private boolean inLayout;
  private TableFilterHeader filterHeader;
  @SuppressWarnings("rawtypes")
  private List<AbstractTableRule> rules = new ArrayList<AbstractTableRule>();


  public JTableAlternated(DefaultTableModel defaultTableModel) {
    super(defaultTableModel);
    init();

  }


  public void clearFilter(int modelColumnIndex) {
    filterHeader.getFilterEditor(modelColumnIndex).resetFilter();
  }


  public JTableAlternated(Object[][] dados, Object[] column) {
    super(dados, column);
    init();
  }


  public void init() {
    filterHeader = new TableFilterHeader(this, AutoChoices.ENABLED);
    filterHeader.setFont(this.getFont());
    setAutoCreateRowSorter(true);
    setColumnControlVisible(true);
  }


  /**
   * return a tooltip message to the user
   */
  @Override
  public String getToolTipText(java.awt.event.MouseEvent e) {
    String tipMessage = null;
    java.awt.Point p = e.getPoint();
    int rowIndex = rowAtPoint(p);
    int colIndex = columnAtPoint(p);

    try {
      // comment row, exclude heading

      tipMessage = getValueAt(rowIndex, colIndex).toString();

    }
    catch (RuntimeException e1) {
      // catch null pointer exception if mouse is over an empty line
    }
    if (tipMessage == null || tipMessage.isEmpty())
      return null;
    return tipMessage;
  }


  /**
   * colorize the background with normal and green if doesn't match the rule colorize with gray
   **/
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    Component c = super.prepareRenderer(renderer, row, column);

    // Alternate row color

    if (!isRowSelected(row)) {
      c.setBackground(row % 2 == 0 ? getBackground() : new Color(188, 255, 188));
      for (AbstractTableRule rule : rules) {
        if (rule.isColorize() && rule.match(getValueAt(row, rule.getAnalizedColumn())))
          c.setBackground(row % 2 == 0 ? rule.getColor().brighter() : rule.getColor().darker());
      }
    }
    else {
      c.setForeground(getForeground());
    }

    return c;
  }


  /**
   * add a rule that blocks and/or colorize with gray the cell
   * 
   * @param <T>
   */
  @SuppressWarnings("rawtypes")
  public void addRule(AbstractTableRule rule) {
    rules.add(rule);
  }


  @SuppressWarnings("rawtypes")
  public <T> void removeRule(AbstractTableRule rule) {
    rules.remove(rule);
  }


  /*****
   * return false to cell that doesn't match the rule
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public boolean isCellEditable(int row, int column) {
    for (AbstractTableRule rule : rules) {
      if (rule.isColorize() && rule.match(getValueAt(row, rule.getAnalizedColumn())))
        return !rule.match(getValueAt(row, rule.getAnalizedColumn()));
    }
    return false;
  }


  @Override
  public boolean getScrollableTracksViewportWidth() {
    return hasExcessWidth();

  }


  @Override
  public void doLayout() {
    if (hasExcessWidth()) {
      // fool super
      autoResizeMode = AUTO_RESIZE_SUBSEQUENT_COLUMNS;
    }
    inLayout = true;
    super.doLayout();
    inLayout = false;
    autoResizeMode = AUTO_RESIZE_OFF;
  }


  protected boolean hasExcessWidth() {
    return getPreferredSize().width < getParent().getWidth();
  }


  @Override
  public void columnMarginChanged(ChangeEvent e) {

    if (isEditing()) {
      // JW: darn - cleanup to terminate editing ...
      removeEditor();
    }
    TableColumn resizingColumn = getTableHeader().getResizingColumn();
    // Need to do this here, before the parent's
    // layout manager calls getPreferredSize().
    if (resizingColumn != null && autoResizeMode == AUTO_RESIZE_OFF && !inLayout) {
      resizingColumn.setPreferredWidth(resizingColumn.getWidth());
    }
    resizeAndRepaint();
  }

}
