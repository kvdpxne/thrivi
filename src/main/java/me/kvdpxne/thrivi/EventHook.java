package me.kvdpxne.thrivi;

import java.lang.reflect.Method;

public class EventHook {

  private final Method method;
  private final Listenable listener;
  private final int priority;
  private final boolean ignoreCancelled;

  public EventHook(Listenable listener, Method method, EventHandler handler) {
    this.listener = listener;
    this.method = method;
    this.priority = handler.priority();
    this.ignoreCancelled = handler.ignoreCancelled();
  }

  public Method getMethod() {
    return method;
  }

  public Listenable getListener() {
    return listener;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isIgnoreCancelled() {
    return ignoreCancelled;
  }
}