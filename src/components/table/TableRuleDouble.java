package components.table;

import java.awt.Color;

public class TableRuleDouble extends AbstractTableRule<Double> {

  public TableRuleDouble(int columnIndex, Double value, Operator operator, Color color) {
    super(columnIndex, value, operator, color);

  }


  public TableRuleDouble(int columnIndex, Double value, Operator operator, Color color, boolean block, boolean colorize) {
    super(columnIndex, value, operator, color, block, colorize);
  }


  @Override
  public boolean match(Double msg) {

    if (condition.equals(Operator.EQUALS))
      if (msg.equals(value))
        return true;
    if (condition.equals(Operator.GREATER))
      if (msg.compareTo(value) > 0)
        return true;
    if (condition.equals(Operator.LESS))
      if (msg.compareTo(value) < 0)
        return true;
    if (condition.equals(Operator.GREATER_OR_EQUALS))
      if (msg.compareTo(value) >= 0)
        return true;
    if (condition.equals(Operator.LESS_OR_EQUALS))
      if (msg.compareTo(value) <= 0)
        return true;
    if (condition.equals(Operator.DIFFERENT))
      if (msg.compareTo(value) != 0)
        return true;
    return false;

  }

}
