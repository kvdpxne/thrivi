package me.kvdpxne.thrivi;

public class EventHook<E extends Event> {

  private final EventHandler<E> eventHandler;
  private final boolean ignoreCancelled;
  private final int priority;

  public EventHook(
    final EventHandler<E> eventHandler,
    final boolean ignoreCancelled,
    final int priority
  ) {
    this.eventHandler = eventHandler;
    this.ignoreCancelled = ignoreCancelled;
    this.priority = priority;
  }

  public void handle(final E event) {
    this.eventHandler.handle(event);
  }

  public boolean isIgnoreCancelled() {
    return this.ignoreCancelled;
  }

  public int getPriority() {
    return this.priority;
  }
}