package me.kvdpxne.thrivi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

  boolean ignoreCancelled() default false;

  int priority() default StandardEventPriorities.NORMAL;
}