package model;

public class Ponto {

  public final String funcionario;
  public Time entrada;
  public Time saida;

  public Ponto(String str) {
    super();
    this.funcionario = str;
  }

  public void bater(String str) {
    if(this.entrada != null && this.saida != null){
      throw new IllegalStateException("Ponto fechado.");
    } else if(this.entrada == null && this.saida == null){
      this.entrada = Time.fromString(str);
    } else if (this.entrada != null && this.saida == null){
      this.saida = Time.fromString(str);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Ponto){
      Ponto outroPonto = (Ponto) obj;
      if(this.funcionario == outroPonto.funcionario){
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    if(this.entrada == null && this.saida == null){
      return String.format("%s não bateu o ponto", this.funcionario);
    } else if(this.entrada != null && this.saida == null){
      return String.format("%s entrou às %s", this.funcionario, this.entrada.toShortString());
    } else if(this.entrada != null && this.saida != null){
      Time permanencia = this.saida.minus(this.entrada);
      return String.format("%s entrou às %s e saiu às %s e permaneceu %s", this.funcionario, this.entrada.toShortString(), this.saida.toShortString(), permanencia.toLongString());
    }
    return null;
  }

}
