package beans;

public class Point {

  private Double x;
  private Double y;
  private Integer zone;
  private Double analysisValue;

  // TODO tem que verificar a necessidade deste z
  private int z;


  public Point(double x, double y, int z, int zone) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  public Point(double x, double y, int zone) {
    this.x = x;
    this.y = y;
  }


  public Double getX() {
    return x;
  }


  public void setX(Double x) {
    this.x = x;
  }


  public Double getY() {
    return y;
  }


  public void setY(Double y) {
    this.y = y;
  }


  public int getZ() {
    return z;
  }


  public void setZ(int z) {
    this.z = z;
  }


  public Integer getZone() {
    return zone;
  }


  public void setZone(Integer zone) {
    this.zone = zone;
  }


  public Double getAnalysisValue() {
    return analysisValue;
  }


  public void setAnalysisValue(Double analysisValue) {
    this.analysisValue = analysisValue;
  }

}
