package net.darkhax.pricklemc.common.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.darkhax.pricklemc.common.api.config.property.IPropertyAdapter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Adapter {
   Class<? extends IPropertyAdapter<?>> value();
}
