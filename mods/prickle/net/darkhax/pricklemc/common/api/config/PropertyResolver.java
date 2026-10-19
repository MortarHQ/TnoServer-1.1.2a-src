package net.darkhax.pricklemc.common.api.config;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.darkhax.pricklemc.common.api.annotations.Adapter;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import net.darkhax.pricklemc.common.api.config.comment.ICommentResolver;
import net.darkhax.pricklemc.common.api.config.property.ConfigObjectProperty;
import net.darkhax.pricklemc.common.api.config.property.IConfigProperty;
import net.darkhax.pricklemc.common.api.config.property.IPropertyAdapter;
import net.darkhax.pricklemc.common.api.config.property.ObjectProperty;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class PropertyResolver {
   private final List<IPropertyAdapter<?>> propertyAdapters;
   private final Map<Class<?>, IPropertyAdapter<?>> adapterCache = new HashMap<>();
   private final IPropertyAdapter<?> configObjectAdapter;
   private final Gson gson;
   private final ICommentResolver commentResolver;
   private final Logger logger;

   public PropertyResolver(Gson gson, Logger logger, List<IPropertyAdapter<?>> propertyAdapters, ICommentResolver commentResolver) {
      this.propertyAdapters = propertyAdapters;
      this.gson = gson;
      this.logger = logger;
      this.configObjectAdapter = ConfigObjectProperty.adapter(this);
      this.commentResolver = commentResolver;
   }

   public Gson gson() {
      return this.gson;
   }

   @Nullable
   public IComment toComment(Field field, Object value, Value valueMeta) throws IOException {
      return this.commentResolver.resolve(field, value, valueMeta);
   }

   public Logger logger() {
      return this.logger;
   }

   public IConfigProperty<?> toProperty(Field field, Object parent, Value cfgValue) throws IOException, IllegalAccessException {
      Object propertyValue = field.get(parent);
      Adapter adapterOverride = field.getAnnotation(Adapter.class);
      if (adapterOverride != null) {
         return this.adapterCache.computeIfAbsent(adapterOverride.value(), clazz -> {
            try {
               if (clazz.getConstructor().newInstance() instanceof IPropertyAdapter<?> adapterInst) {
                  return adapterInst;
               } else {
                  throw new IllegalArgumentException("Adapter override on field '" + field.getName() + "' must implement IPropertyAdapter!");
               }
            } catch (Exception e) {
               throw new RuntimeException(ex);
            }
         }).toValue(this, field, parent, propertyValue, cfgValue);
      }

      for (IPropertyAdapter<?> adapter : this.propertyAdapters) {
         try {
            IConfigProperty<?> property = adapter.toValue(this, field, parent, propertyValue, cfgValue);
            if (property != null) {
               return property;
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }

      IConfigProperty<?> configObjProperty = this.configObjectAdapter.toValue(this, field, parent, propertyValue, cfgValue);
      return configObjProperty != null ? configObjProperty : ObjectProperty.FALLBACK_ADAPTER.toValue(this, field, parent, propertyValue, cfgValue);
   }
}
