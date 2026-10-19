package me.wesley1808.servercore.common.config.serialization;

import me.wesley1808.servercore.common.config.Config;
import me.wesley1808.servercore.common.services.platform.PlatformHelper;
import net.minecraft.world.entity.EntityType;
import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

public class EntityTypeSerializer implements ValueSerialiser<EntityType> {
   public Class<EntityType> getTargetClass() {
      return EntityType.class;
   }

   public EntityType<?> deserialise(FlexibleType flexibleType) throws BadValueException {
      String key = flexibleType.getString();
      EntityType<?> type = PlatformHelper.getEntityType(key);
      if (type == null) {
         if (Config.shouldValidate()) {
            throw flexibleType.badValueExceptionBuilder().message("Unknown entity type: " + key).build();
         }

         type = EntityType.MARKER;
      }

      return type;
   }

   public Object serialise(EntityType value, Decomposer decomposer) {
      return PlatformHelper.getEntityTypeKey(value).toString();
   }
}
