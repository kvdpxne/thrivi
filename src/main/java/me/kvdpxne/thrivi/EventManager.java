package me.kvdpxne.thrivi;

import java.util.List;

public interface EventManager {

  List<EventHook<? super Event>> getRegisteredEventHooks();

  void addEvent(final Class<? extends Event> eventClass);

  void removeEvent(final Class<? extends Event> eventClass);

  <E extends Event> void registerEventHook(
    final Class<? extends Event> eventClass,
    final EventHook<E> eventHook
  );

  <E extends Event> void unregisterEventHook(
    final Class<? extends Event> eventClass,
    final EventHook<E> eventHook
  );

  <E extends Event> E callEvent(
    final E event
  );
}