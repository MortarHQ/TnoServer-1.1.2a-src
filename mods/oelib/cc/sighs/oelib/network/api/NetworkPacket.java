package cc.sighs.oelib.network.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NetworkPacket {
   String modId();

   String id();

   Side side() default Side.BOTH;

   int priority() default 1000;

   int chunkThreshold() default 0;
}
