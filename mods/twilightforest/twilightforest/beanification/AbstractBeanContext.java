package twilightforest.beanification;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
abstract class AbstractBeanContext {
   private Map<BeanDefinition<?>, Object> BEANS = new HashMap<>();
   private boolean frozen = false;

   Map<BeanDefinition<?>, Object> getBeans() {
      return this.BEANS;
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   protected void freeze() {
      this.BEANS = Collections.unmodifiableMap(this.BEANS);
      this.frozen = true;
   }

   protected boolean canAccessUnfrozen() {
      return false;
   }

   protected void registerInternal(Class<?> type, @Nullable String name, Object instance) {
      if (this.frozen) {
         throw new IllegalStateException("Bean Context already frozen");
      }

      BeanDefinition<?> beanDefinition = new BeanDefinition<>(type, name);
      if (this.BEANS.containsKey(beanDefinition)) {
         StringBuilder error = new StringBuilder("Class: ").append(type);
         if (name != null) {
            error.append(", Name: ").append(name);
         }

         throw new RuntimeException("Attempted to register a duplicate Bean." + error);
      } else {
         this.BEANS.put(beanDefinition, instance);
      }
   }

   <T> T injectInternal(Class<T> type, @Nullable String name) {
      if (!this.frozen && !this.canAccessUnfrozen()) {
         throw new IllegalStateException("Bean Context has not been initialized yet");
      } else {
         return type.cast(
            Objects.requireNonNull(this.BEANS.get(new BeanDefinition<>(type, name)), "Trying to inject Bean: " + type + (name == null ? "" : " (" + name + ")"))
         );
      }
   }
}
