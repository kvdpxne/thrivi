package me.kvdpxne.thrivi;

import java.util.Collection;
import java.util.List;

public interface EventManager {

  <E extends Event> List<EventHook<? super Event>> getRegisteredEventHooks();

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