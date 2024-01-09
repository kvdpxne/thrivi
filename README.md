## Usage
Create your own event by extending the ``Event`` or ``CancellableEvent``
object if you want your event to be cancelable.
```java
import me.kvdpxne.thrivi.CancellableEvent;

public class OurEvent extends CancellableEvent {

  private final String projectName;

  public OurEvent(final String projectName) {
    this.projectName = projectName;
  }

  public String getProjectName() {
    return this.projectName;
  }
}
```

Create a listener for your event or events. It does not matter whether there
will be 1 or more event handlers in one object.
```java
import me.kvdpxne.thrivi.EventHandler;
import me.kvdpxne.thrivi.EventListener;

public class OurEventListener implements EventListener {

  // Access modifiers doesn't matter.
  final EventHandler<OurEvent> ourEventHandler = this.handler(event -> {
    final String name = event.getProjectName();
    System.out.printf("The name of this project is: %s%n", name);

    if (name.equals("thrivi")) {
      System.out.printf("Yes, our project name is %s%n", name);
    }
  });
}
```

Register your previously created event and event listener.
```java
import me.kvdpxne.thrivi.BuiltInEventManager;

public final class Main {

  public static void main(final String[] args) {
    // 1. Register event.
    BuiltInEventManager.INSTANCE.addEvent(OurEvent.class);

    // 2. Register event listener.
    // The easiest way to register the listener of our event in this case will
    // be to simply initialize the object.
    new OurEventListener();

    // 3. trigger the registered event whenever and wherever you want.
    final String[] names = {
      "event",
      "event-listener",
      "thrivi"
    };

    for (final String name : names) {
      BuiltInEventManager.INSTANCE.callEvent(new OurEvent(name));
    }
  }
}
```