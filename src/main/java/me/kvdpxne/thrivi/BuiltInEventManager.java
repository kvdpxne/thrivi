package me.kvdpxne.thrivi;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum BuiltInEventManager
  implements EventManager {

  INSTANCE;

  private static final Logger logger = LoggerFactory.getLogger(EventManager.class);
  private final Map<Class<? extends Event>, List<EventHook<? super Event>>> events;

  BuiltInEventManager() {
    this.events = new HashMap<>();
  }

  @Override
  public List<EventHook<? super Event>> getRegisteredEventHooks() {
    return this.events.values()
      .stream()
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
  }

  @Override
  public void addEvent(final Class<? extends Event> eventClass) {
    this.events.putIfAbsent(
      eventClass,
      new CopyOnWriteArrayList<>()
    );
  }

  @Override
  public void removeEvent(final Class<? extends Event> eventClass) {
    this.events.remove(eventClass);
  }

  @Override
  public <E extends Event> void registerEventHook(
    final Class<? extends Event> eventClass,
    final EventHook<E> eventHook
  ) {
    final List<EventHook<? super Event>> handlers = this.events.get(eventClass);
    if (null == handlers) {
      throw new EventHookNotFoundException(
        "The event \"%s\" was not registered.",
        eventClass.getName()
      );
    }
    final EventHook<? extends Event> hook = eventHook;
    if (!handlers.contains(hook)) {
      handlers.add((EventHook<? super Event>) hook);
      handlers.sort(Comparator.comparing(EventHook::getPriority));
    }
  }

  @Override
  public <E extends Event> void unregisterEventHook(
    final Class<? extends Event> eventClass,
    final EventHook<E> eventHook
  ) {
    final List<EventHook<? super Event>> eventHooks = this.events.get(eventClass);
    if (null == eventHooks) {
      return;
    }
    eventHooks.remove(eventHook);
  }

  @Override
  public <E extends Event> E callEvent(
    final E event
  ) {
    Objects.requireNonNull(
      event,
      "The passed parameter named event is null."
    );
    final Class<? extends Event> clazz = event.getClass();
    final List<EventHook<? super Event>> hooks = this.events.get(clazz);
    //
    //
    if (null == hooks) {
      throw new EventHookNotFoundException(
        "The event \"%s\" was not registered.",
        clazz.getName()
      );
    }
    boolean isCancelled = false;
    if (event instanceof Cancellable) {
      isCancelled = ((Cancellable) event).isCancelled();
    }
    for (final EventHook<? super Event> hook : hooks) {
      // Skip event handling execution for these events that were canceled and
      // are not flagged to ignore event cancellation.
      if (isCancelled && !hook.isIgnoreCancelled()) {
        continue;
      }
      try {
        //
        hook.handle(event);
      } catch (final Throwable exception) {
        logger.error(
          "Exception while executing handler.",
          exception.getCause()
        );
      }
    }
    return event;
  }
}
