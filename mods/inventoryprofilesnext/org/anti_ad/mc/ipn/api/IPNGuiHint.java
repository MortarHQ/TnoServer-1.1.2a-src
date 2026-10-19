package org.anti_ad.mc.ipn.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(IPNGuiHints.class)
public @interface IPNGuiHint {
   IPNButton button();

   int top() default 0;

   int bottom() default 0;

   int horizontalOffset() default 0;

   boolean hide() default false;
}
