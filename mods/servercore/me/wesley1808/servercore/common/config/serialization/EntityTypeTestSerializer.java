package me.wesley1808.servercore.common.config.serialization;

import me.wesley1808.servercore.common.activation_range.EntityTypeTests;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

public class EntityTypeTestSerializer implements ValueSerialiser<EntityTypeTest> {
   private static final String PREFIX = "typeof:";

   public Class<EntityTypeTest> getTargetClass() {
      return EntityTypeTest.class;
   }

   public EntityTypeTest deserialise(FlexibleType flexibleType) throws BadValueException {
      String key = flexibleType.getString();
      if (!key.startsWith("typeof:")) {
         return (EntityTypeTest)flexibleType.getObject(EntityType.class);
      } else {
         EntityTypeTest<?, ?> test = EntityTypeTests.get(key.substring("typeof:".length()));
         if (test == null) {
            throw flexibleType.badValueExceptionBuilder().message("Unknown typeof class matcher: " + key).build();
         } else {
            return test;
         }
      }
   }

   public Object serialise(EntityTypeTest value, Decomposer decomposer) {
      return value instanceof EntityType<?> type ? decomposer.decompose(EntityType.class, type) : "typeof:" + EntityTypeTests.getKey(value);
   }
}
