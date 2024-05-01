package br.com.garage.kbn.shared;

public class MessageError {
  public int code;
  public String message;

  public MessageError(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
