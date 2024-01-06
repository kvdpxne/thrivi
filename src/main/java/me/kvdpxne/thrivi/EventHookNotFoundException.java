package me.kvdpxne.thrivi;

public final class EventHookNotFoundException extends RuntimeException {

  public EventHookNotFoundException(
    final String message,
    final Object... arguments
  ) {
    super(String.format(message, arguments));
  }
}
