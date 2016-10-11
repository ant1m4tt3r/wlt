package components.table;

import java.awt.Color;

public abstract class AbstractTableRule<T> {

  protected int analizedColumn;
  protected T value;
  protected Operator condition;

  private Color color;
  private boolean block = false;
  private boolean colorize = true;

  public enum Operator {
    EQUALS, GREATER, LESS, GREATER_OR_EQUALS, LESS_OR_EQUALS, DIFFERENT;
  }


  public AbstractTableRule(int columnIndex, T value, Operator operator, Color color) {
    this.analizedColumn = columnIndex;
    this.value = value;
    this.condition = operator;
    this.color = color;
  }


  public AbstractTableRule(int columnIndex, T value, Operator operator, Color color, boolean block, boolean colorize) {
    this.analizedColumn = columnIndex;
    this.value = value;
    this.condition = operator;
    this.color = color;
    this.block = block;
    this.colorize = colorize;
  }

  public abstract boolean match(T fieldValue);

  public int getAnalizedColumn() {
    return analizedColumn;
  }


  public void setAnalizedColumn(int analizedColumn) {
    this.analizedColumn = analizedColumn;
  }


  public T getValue() {
    return value;
  }


  public void setValue(T value) {
    this.value = value;
  }


  public Operator getCondition() {
    return condition;
  }


  public void setCondition(Operator condition) {
    this.condition = condition;
  }


  public Color getColor() {
    return color;
  }


  public void setColor(Color color) {
    this.color = color;
  }


  public boolean isBlock() {
    return block;
  }


  public void setBlock(boolean block) {
    this.block = block;
  }


  public boolean isColorize() {
    return colorize;
  }


  public void setColorize(boolean colorize) {
    this.colorize = colorize;
  }
  
  
}
