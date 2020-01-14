package at.ac.tuwien.big.ame.somqm.server.service;

public class ServiceException extends Exception {

  private static final long serialVersionUID = 8375241096351843000L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
