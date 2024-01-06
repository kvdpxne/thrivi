package me.kvdpxne.thrivi;

import net.jodah.typetools.TypeResolver;

public interface EventListener {

  default <E extends Event> EventHandler<E> handler(
    final boolean ignoreCancelled,
    final int priority,
    final EventHandler<E> handler
  ) {
    final EventHook<E> eventHook = new EventHook<>(handler, ignoreCancelled, priority);
    final Class<? extends Event> clazz = (Class<? extends Event>) TypeResolver.resolveRawArgument(EventHandler.class, handler.getClass());
    BuiltInEventManager.INSTANCE.registerEventHook(clazz, eventHook);
    return handler;
  }

  default <E extends Event> EventHandler<E> handler(
    final boolean ignoreCancelled,
    final EventHandler<E> handler
  ) {
    return this.handler(
      ignoreCancelled,
      StandardEventPriorities.NORMAL,
      handler
    );
  }

  default <E extends Event> EventHandler<E> handler(
    final int priority,
    final EventHandler<E> handler
  ) {
    return this.handler(
      false,
      priority,
      handler
    );
  }

  default <E extends Event> EventHandler<E> handler(
    final EventHandler<E> handler
  ) {
    return this.handler(
      false,
      StandardEventPriorities.NORMAL,
      handler
    );
  }
}
