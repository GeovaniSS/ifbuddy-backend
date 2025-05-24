package br.com.ifbuddy.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {
  public static String formatarDataHora(LocalDateTime dataHora) {
    if (dataHora == null)
      return null;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return dataHora.format(formatter);
  }
}
