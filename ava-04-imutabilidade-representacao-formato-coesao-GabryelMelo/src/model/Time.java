package model;
import static java.lang.Integer.parseInt;

public class Time {

  public final int seconds;

  public static final Time MIDDAY = new Time(12, 0, 0);
  public static final Time MIDNIGHT = new Time(0, 0, 0);
  static final int HOURS_PER_DAY = 24;
  static final int MINUTES_PER_HOUR = 60;
  static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
  static final int SECONDS_PER_MINUTE = 60;
  static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
  static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

  public Time(int h, int m, int s) {
    this.seconds = (h * SECONDS_PER_HOUR + m * SECONDS_PER_MINUTE + s) % SECONDS_PER_DAY;
  }

  public Time(int h, int m) {
    this.seconds = (h * SECONDS_PER_HOUR + m * SECONDS_PER_MINUTE);
  }

  public Time(int h) {
    this.seconds = (h * SECONDS_PER_HOUR);
  }

  public Time() {
    this.seconds = 0;
  }

  public Time(Time t1) {
    this.seconds = t1.seconds;
  }

  public int getHours() {
    return this.seconds / 3600;
  }

  public int getMinutes() {
    return (this.seconds % 3600) / 60;
  }

  public int getSeconds() {
    return (this.seconds % 3600) % 60;
  }

  public Time plusHours(int h) {
    if (h == 0) {
      return this;
    }
    return this.plusSeconds((h * SECONDS_PER_HOUR));
  }

  public Time plusMinutes(int m) {
    if (m == 0) {
      return this;
    }
    return this.plusSeconds((m * SECONDS_PER_MINUTE));
  }

  public Time plusSeconds(int s) {
    if (s == 0) {
      return this;
    }
    int secAux = this.seconds;

    if(secAux + s > 86399){
      secAux = (secAux + s) % SECONDS_PER_DAY;

      int hours = secAux / 3600;
      int minutes = (secAux % 3600) / 60;
      int seconds = (secAux % 3600) % 60;
      return new Time(hours, minutes, seconds);
    }
    if(s < 0){
      s = s * (-1);
      return this.minusSeconds(s);
    }
    secAux = secAux + s;

    int hours = secAux / 3600;
    int minutes = (secAux % 3600) / 60;
    int seconds = (secAux % 3600) % 60;
    return new Time(hours, minutes, seconds);
  }

  public Time plus(Time t1) {
    return this.plusSeconds(t1.seconds);
  }

  public Time minusHours(int h) {
    if (h == 0) {
      return this;
    }
    return this.minusSeconds((h * SECONDS_PER_HOUR));
  }

  public Time minusMinutes(int m) {
    if (m == 0) {
      return this;
    }
    return this.minusSeconds((m * SECONDS_PER_MINUTE));
  }

  public Time minusSeconds(int s) {
    if(s == 0){
      return this;
    }
    int secAux = this.seconds;

    if(secAux - s < 0){
      secAux = (secAux - s) % SECONDS_PER_DAY;
      if(secAux < 0){
        secAux = SECONDS_PER_DAY - (secAux * (-1));
      }
      int hours = secAux / 3600;
      int minutes = (secAux % 3600) / 60;
      int seconds = (secAux % 3600) % 60;
      return new Time(hours, minutes, seconds);
    }
    if(s < 0){
      s = s * (-1);
      return this.plusSeconds(s);
    }
    secAux = secAux - s;

    int hours = secAux / 3600;
    int minutes = (secAux % 3600) / 60;
    int seconds = (secAux % 3600) % 60;
    return new Time(hours, minutes, seconds);
  }

  public Time minus(Time t1) {
    return this.minusSeconds(t1.seconds);
  }

  public boolean isMidDay() {
    if(this.seconds == 43200){
      return true;
    }
    return false;
  }

  public boolean isMidNight() {
    if(this.seconds == 0){
      return true;
    }
    return false;
  }

  public Time shift() {
    return this.plusSeconds(43200);
  }

  public Time tick() {
    return this.plusSeconds(1);
  }

  public static Time fromString(String string) {
    String[] str = string.split(":");
    int hours = parseInt(str[0]);
    int minutes = parseInt(str[1]);
    int seconds = parseInt(str[2]);
    return new Time(hours, minutes, seconds);
  }

  public static Time fromDouble(double d) {
    Double db = d * 3600;
    int secAux = db.intValue();
    int hours = secAux / 3600;
    int minutes = (secAux % 3600) / 60;
    int seconds = (secAux % 3600) % 60;
    return new Time(hours, minutes, seconds);
  }

  public static Time fromSeconds(int i) {
    int hours = i / 3600;
    int minutes = (i % 3600) / 60;
    int seconds = (i % 3600) % 60;
    return new Time(hours, minutes, seconds);
  }

  public static Time from(Time t) {
    int hours = t.seconds / 3600;
    int minutes = (t.seconds % 3600) / 60;
    int seconds = (t.seconds % 3600) % 60;
    return new Time(hours, minutes, seconds);
  }

  public double toDouble() {
    double db = this.seconds / 3600.0;
    return db;
  }

  public int toInt() {
    return this.seconds;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (null == obj)
      return false;
    if (obj instanceof Time) {
      Time anotherTime = (Time) obj;
      return this.seconds == anotherTime.seconds;
    }
    return false;
  }

  @Override
  public String toString() {
    int hours = this.seconds / 3600;
    int minutes = (this.seconds % 3600) / 60;
    int seconds = (this.seconds % 3600) % 60;
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  public String toLongString() {
    int hours = this.seconds / 3600;
    int minutes = (this.seconds % 3600) / 60;
    int seconds = (this.seconds % 3600) % 60;

    if (seconds == 0 && hours > 1 && minutes > 1){ // Não imprime números iguais a zero, e imprime o restante no plural
      return String.format("%d horas e %d minutos", hours, minutes);
    } else if (minutes == 0 && hours > 1 && seconds > 1){
      return String.format("%d horas e %d segundos", hours, seconds);
    } else if (hours == 0 && minutes > 1 && seconds > 1){
      return String.format("%d minutos e %d segundos", minutes, seconds);
    } else if (seconds == 0 && hours == 1 && minutes > 1) { // Não imprime números iguais a zero, imprime um no singular e outro no plural
      return String.format("%d hora e %d minutos", hours, minutes);
    } else if (minutes == 0 && hours == 1 && seconds > 1) {
      return String.format("%d hora e %d segundos", hours, seconds);
    } else if (hours == 0 && minutes == 1 && seconds > 1) {
      return String.format("%d minuto e %d segundos", minutes, seconds);
    } else if (seconds == 0 && hours > 1 && minutes == 1) { // Não imprime números iguais a zero, e imprime um no plural e outro no singular
      return String.format("%d horas e %d minuto", hours, minutes);
    } else if (minutes == 0 && hours > 1 && seconds == 1) {
      return String.format("%d horas e %d segundo", hours, seconds);
    } else if (hours == 0 && minutes > 1 && seconds == 1) {
      return String.format("%d minutos e %d segundo", minutes, seconds);
    } else if (hours == 1 && minutes == 1 && seconds == 1) { // Imprime todos no singular
      return String.format("%d hora %d minuto e %d segundo", hours, seconds);
    } else if (hours == 1 && minutes == 0 && seconds == 0){
      return String.format("%d hora", hours);
    } else if (hours > 1 && minutes == 0 && seconds == 0) {
      return String.format("%d horas", hours);
    } else if (hours == 0 && minutes == 1 && seconds == 0) {
      return String.format("%d minuto", minutes);
    } else if (hours == 0 && minutes > 1 && seconds == 0) {
      return String.format("%d minutos", minutes);
    } else if (hours == 0 && minutes == 0 && seconds == 1) {
      return String.format("%d segundo", seconds);
    } else if (hours == 0 && minutes == 0 && seconds > 1) {
      return String.format("%d segundos", seconds);
    }

    return String.format("%d horas %d minutos e %d segundos", hours, minutes, seconds);
  }

  public String toShortString() {
    int hours = this.seconds / 3600;
    int minutes = (this.seconds % 3600) / 60;
    int seconds = (this.seconds % 3600) % 60;
    if (minutes == 0 && seconds == 0) {
      return String.format("%02dh", hours);
    } else if(seconds == 0){
      return String.format("%02dh%02dm", hours, minutes);
    } else if(hours == 0){
      return String.format("%02dm%02ds", minutes, seconds);
    }

    return String.format("%02dh%02dm%02ds", hours, minutes, seconds);
  }

}
