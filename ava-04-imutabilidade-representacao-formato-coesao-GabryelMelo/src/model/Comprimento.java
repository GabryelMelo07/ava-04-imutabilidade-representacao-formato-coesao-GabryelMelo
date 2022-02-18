package model;

import static java.lang.Integer.parseInt;

public class Comprimento {

  public enum Unidade {
    POLEGADA(25.4),
    CENTIMETRO(10),
    METRO(1000),
    KILOMETRO(1000000);

    public double milimetros;

    Unidade(double d2) {
      this.milimetros = d2;
    }
  }

  public final int milimetros;

  public Comprimento() {
    this.milimetros = 0;
  }

  public Comprimento(double d) {
    if (d < 0) {
      throw new IllegalArgumentException("Milimetros não pode ser negativo.");
    }
    this.milimetros = (int) (d * 1000);
  }

  public Comprimento(int i) {
    if (i < 0) {
      throw new IllegalArgumentException("Milimetros não pode ser negativo.");
    }
    this.milimetros = i;
  }

  public static Comprimento fromPolegadas(double d) {
    int polegadas = (int) (d * 25.4);
    return new Comprimento(polegadas);
  }

  public static Comprimento fromString(String str) {
    if (str.contains("mm")) {
      String aux = str.replaceAll("mm", "");
      int mm = (int) parseInt(aux);
      return new Comprimento(mm);
    } else if (str.contains("cm")) {
      String aux = str.replaceAll("cm", "");
      int mm = parseInt(aux) * 10;
      return new Comprimento(mm);
    } else if (str.contains("\"")) {
      String aux = str.replaceAll("\"", "");
      int mm = (int) (parseInt(aux) * Unidade.POLEGADA.milimetros);
      return new Comprimento(mm);
    } else if (str.contains("m")) {
      String aux = str.replaceAll("m", "");
      int mm = parseInt(aux) * 1000;
      return new Comprimento(mm);
    } else {
      throw new IllegalArgumentException("Medida incorreta.");
    }
  }

  public double getCentimetros() {
    return this.milimetros / 10.0;
  }

  public double getMetros() {
    return this.milimetros / 1000.0;
  }

  public double getPolegadas() {
    return Math.floor(this.milimetros / 25.4);
  }

  public int getMilimetros() {
    return this.milimetros;
  }

  public Comprimento mais(Comprimento c) {
    return new Comprimento(this.milimetros + c.milimetros);
  }

  public Comprimento mais(double d) {
    return new Comprimento((this.milimetros + (d * 1000)) / 1000);
  }

  public Comprimento mais(int i) {
    return new Comprimento(this.milimetros + i);
  }

  public Comprimento dobro() {
    return new Comprimento(this.milimetros * 2);
  }

  public Comprimento vezes(int i) {
    return new Comprimento(this.milimetros * i);
  }

  public Comprimento menos(int i) {
    return new Comprimento(this.milimetros - i >= 0 ? this.milimetros - i : 0);
  }

  public Comprimento menos(double d) {
    return new Comprimento((this.milimetros - (d * 1000)) / 1000 >= 0 ? (this.milimetros - (d * 1000)) / 1000 : 0);
  }

  public Comprimento[] segmentos(int i) {
    Comprimento[] aux = new Comprimento[i];
    if (this.milimetros % i == 0) {
      for (int j = 0; j < i; j++) {
        aux[j] = new Comprimento(this.milimetros / i);
      }
      return aux;
    } else {
      int a = this.milimetros / i;
      int b = i - 1;
      for (int j = 0; j < b; j++) {
        aux[j] = new Comprimento(a);
      }
      for (int j = b; j < i; j++) {
        int mod = this.milimetros % 3;
        aux[j] = new Comprimento(mod + (this.milimetros / i));
      }
    }
    return aux;
  }

  public static Comprimento fromSegmentos(Comprimento c) {
    return new Comprimento();
  }

  @Override
  public String toString() {
    return String.format("%dmm", this.milimetros);
  }

  public String toString(Unidade u) {
    switch (u) {
    case POLEGADA:
      return String.format("%.2f\"", (this.milimetros / Unidade.POLEGADA.milimetros));
    case CENTIMETRO:
      return String.format("%.0fcm", (this.milimetros / Unidade.CENTIMETRO.milimetros));
    case METRO:
      return String.format("%.0fm", (this.milimetros / Unidade.METRO.milimetros));
    case KILOMETRO:
      return String.format("%.1fkm", (this.milimetros / Unidade.KILOMETRO.milimetros));
    default:
      return null;
    }
  }

}
