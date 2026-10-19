package com.finderfeed.fdlib.systems.screen.annotations;

import com.finderfeed.fdlib.systems.screen.ValueComponent;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VComponent {
   Class<? extends ValueComponent<?>> value();
}
