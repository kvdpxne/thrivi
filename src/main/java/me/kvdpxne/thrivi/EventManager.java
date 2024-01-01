package me.kvdpxne.thrivi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

  private final Map<Class<? extends Event>, List<EventHook>> events =
    new HashMap<>();

  public void registerListener(Listenable listenable) {
    for (Method method : listenable.getClass().getDeclaredMethods()) {
      if (!method.isAnnotationPresent(EventHandler.class) || 1 != method.getParameterTypes().length) {
        continue;
      }

      if (!method.isAccessible()) {
        method.setAccessible(true);
      }

      final Class<? extends Event> eventClass = ((Class<? extends Event>)
        method.getParameterTypes()[0]);
      final EventHandler eventHandler = method.getAnnotation(
        EventHandler.class);

      final List<EventHook> invokableEventHandlers = this.events.getOrDefault(
        eventClass, new ArrayList<>()
      );
      invokableEventHandlers.add(new EventHook(listenable, method,
        eventHandler));
      invokableEventHandlers.sort(Comparator.comparingInt(
        EventHook::getPriority));
      this.events.put(eventClass, invokableEventHandlers);
    }
  }

  public void unregisterListener(Listenable listenable) {
    events.forEach((key, value) -> {
      value.removeIf(hok -> hok.getListener().equals(listenable));
    });
  }

  public void callEvent(Event event) {
    final List<EventHook> eventHooks = this.events.get(event.getClass());
    if (null == eventHooks || eventHooks.isEmpty()) {
      return;
    }
    eventHooks.forEach(eventHook -> {
      if (eventHook.isIgnoreCancelled()) {
        return;
      }
      try {
        eventHook.getMethod().invoke(eventHook.getListener(), event);
      } catch (final Throwable cause) {
        cause.printStackTrace();
      }
    });
  }
}