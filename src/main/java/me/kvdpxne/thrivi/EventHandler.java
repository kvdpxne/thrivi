package me.kvdpxne.thrivi;

@FunctionalInterface
public interface EventHandler<E extends Event> {

  void handle(final E event);
}