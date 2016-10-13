package beans;

import java.util.Date;
import java.util.Map;

public class SamplingPoint {

  private Integer idpontoDeAmostragem;
  private String nome;
  private String cidade;
  private String estado;
  private String color = "gray";
  // X
  private Double latitude;
  // Y
  private Double longitude;
  private String fuso;
  private String datum;
  private String referencia;
  private String referenciaAuxiliar;
  private Date fonte;
  private String laudo;
  private String laudoComplementar;
  private int versao;
  private Double analysisValue;
  private Double lq;
  private int nada;
  private Double limit;
  private String operator;

  


  public SamplingPoint() {
  }


  public SamplingPoint(String nome, String cidade, String estado, Double latitude, Double longitude, String fuso, String datum, String referencia, String referenciaAuxiliar, Date fonte, String laudo, int versao) {
    this.nome = nome;
    this.cidade = cidade;
    this.estado = estado;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fuso = fuso;
    this.datum = datum;
    this.referencia = referencia;
    this.referenciaAuxiliar = referenciaAuxiliar;
    this.fonte = fonte;
    this.laudo = laudo;
    this.versao = versao;
  }


  public SamplingPoint(String nome, String cidade, String estado, Double latitude, Double longitude, String fuso, String datum, String referencia, String referenciaAuxiliar, Date fonte, String laudo, String laudoComplementar, int versao) {
    this.nome = nome;
    this.cidade = cidade;
    this.estado = estado;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fuso = fuso;
    this.datum = datum;
    this.referencia = referencia;
    this.referenciaAuxiliar = referenciaAuxiliar;
    this.fonte = fonte;
    this.laudo = laudo;
    this.laudoComplementar = laudoComplementar;
    this.versao = versao;    
  }


  public Integer getIdpontoDeAmostragem() {
    return this.idpontoDeAmostragem;
  }


  public void setIdpontoDeAmostragem(Integer idpontoDeAmostragem) {
    this.idpontoDeAmostragem = idpontoDeAmostragem;
  }


  public String getNome() {
    return this.nome;
  }


  public void setNome(String nome) {
    this.nome = nome;
  }


  public String getCidade() {
    return this.cidade;
  }


  public void setCidade(String cidade) {
    this.cidade = cidade;
  }


  public String getEstado() {
    return this.estado;
  }


  public void setEstado(String estado) {
    this.estado = estado;
  }


  /***
   * latitude equals x
   * 
   * @return
   */
  public Double getLatitude() {
    return latitude;
  }


  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }


  /***
   * longitude equals y
   * 
   * @return
   */
  public Double getLongitude() {
    return longitude;
  }


  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }


  public String getFuso() {
    return this.fuso;
  }


  public void setFuso(String fuso) {
    this.fuso = fuso;
  }


  public String getDatum() {
    return this.datum;
  }


  public void setDatum(String datum) {
    this.datum = datum;
  }


  public String getReferencia() {
    return this.referencia;
  }


  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }


  public String getReferenciaAuxiliar() {
    return this.referenciaAuxiliar;
  }


  public void setReferenciaAuxiliar(String referenciaAuxiliar) {
    this.referenciaAuxiliar = referenciaAuxiliar;
  }


  public Date getFonte() {
    return this.fonte;
  }


  public void setFonte(Date fonte) {
    this.fonte = fonte;
  }


  public String getLaudo() {
    return this.laudo;
  }


  public void setLaudo(String laudo) {
    this.laudo = laudo;
  }


  public String getLaudoComplementar() {
    return this.laudoComplementar;
  }


  public void setLaudoComplementar(String laudoComplementar) {
    this.laudoComplementar = laudoComplementar;
  }


  public int getVersao() {
    return this.versao;
  }


  public void setVersao(int versao) {
    this.versao = versao;
  }


  


  public Double getAnalysisValue() {
    return analysisValue;
  }


  public void setAnalysisValue(Double analysisValue) {
    this.analysisValue = analysisValue;
  }


  public Double getLq() {
    return lq;
  }


  public void setLq(Double lq) {
    this.lq = lq;
  }


  public Double getLimit() {
    return limit;
  }


  public void setLimit(Double limit) {
    this.limit = limit;
  }


  public String getOperator() {
    return operator;
  }


  public void setOperator(String operator) {
    this.operator = operator;
  }


  public String getColor() {
    return color;
  }


  public void setColor(String color, Map<String, String> mapPontos) {
    if (mapPontos.get(this.nome) != null) {
      int colorValue = getColorValue(color);
      int oldColorValue = getColorValue(mapPontos.get(this.nome));
      if (colorValue > oldColorValue) {
        mapPontos.put(this.nome, color);
        this.color = mapPontos.get(this.nome);
      }
      else {
        this.color = color;
      }

    }
    else {
      mapPontos.put(this.nome, color);
      this.color = color;
    }
  }


  private int getColorValue(String color) {
    switch (color) {
      case "black":
        return 0;
      case "blue":
        return 1;
      case "green":
        return 2;
      case "yellow":
        return 3;
      case "orange":
        return 4;
      case "red":
        return 5;

      default:
        return -1;
    }
  }

}
