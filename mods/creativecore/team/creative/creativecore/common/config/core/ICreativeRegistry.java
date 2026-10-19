package team.creative.creativecore.common.config.core;

import java.lang.reflect.Field;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;

public interface ICreativeRegistry {
   ConfigEqualChecker getEqualChecker();

   boolean is(Field var1);

   default boolean is(Field field, Side side) {
      if (!this.is(field)) {
         return false;
      }

      CreativeConfig config = field.getAnnotation(CreativeConfig.class);
      return config == null || config.type().useValue(false, side);
   }

   default boolean equals(Object one, Object two, Side side) {
      return this.getEqualChecker().equals(one, two, side, this);
   }
}
