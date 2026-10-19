package net.darkhax.pricklemc.common.api.config.property;

import java.io.IOException;
import java.lang.reflect.Field;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import org.jetbrains.annotations.Nullable;

public interface IPropertyAdapter<T extends IConfigProperty<?>> {
   @Nullable
   T toValue(PropertyResolver var1, Field var2, Object var3, @Nullable Object var4, Value var5) throws IOException;
}
