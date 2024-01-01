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

      if (!method.canAccess(listenable)) {
        method.setAccessible(true);
      }

      var eventClass = ((Class<? extends Event>) method.getParameterTypes()[0]);
      var eventHandler = method.getAnnotation(EventHandler.class);

      var invokableEventHandler = events.getOrDefault(eventClass, new ArrayList<>());
      invokableEventHandler.add(new EventHook(listenable, method,
        eventHandler));

      invokableEventHandler.sort(Comparator.comparingInt(EventHook::getPriority));
      events.put(eventClass, invokableEventHandler);
    }
  }

  public void unregisterListener(Listenable listenable) {
    events.forEach((key, value) -> {
      value.removeIf(hok -> hok.getListener().equals(listenable));
    });
  }

  public void callEvent(Event event) {
    var targets = events.get(event.getClass());
    if (null == targets) {
      return;
    }
    targets.forEach(target -> {
      if (!target.isIgnoreCancelled()) {
        return;
      }
      try {
        target.getMethod().invoke(target.getListener(), event);
      } catch (final Throwable cause) {
        cause.printStackTrace();
      }
    });
  }
}