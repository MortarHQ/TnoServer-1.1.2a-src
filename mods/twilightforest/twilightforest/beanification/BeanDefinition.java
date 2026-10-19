package twilightforest.beanification;

import java.util.Objects;
import javax.annotation.Nullable;

public record BeanDefinition<T>(Class<T> type, @Nullable String name) {
   @Override
   public int hashCode() {
      return Objects.hash(this.type, this.name);
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof BeanDefinition(Class<?> typeOther, String nameOther) && this.type.equals(typeOther) && Objects.equals(this.name, nameOther);
   }
}
