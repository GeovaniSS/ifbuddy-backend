package br.com.ifbuddy.utils;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.nio.charset.StandardCharsets;

public class BlobUtils {

  public static Blob stringParaBlob(String conteudo) {
    if (conteudo == null) {
      return null;
    }
    try {
      byte[] bytes = conteudo.getBytes(StandardCharsets.UTF_8);
      return new SerialBlob(bytes);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao converter String para Blob", e);
    }
  }

  public static String blobParaString(Blob blob) {
    if (blob == null) {
      return null;
    }
    try {
      byte[] bytes = blob.getBytes(1, (int) blob.length());
      return new String(bytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao converter Blob para String", e);
    }
  }
}