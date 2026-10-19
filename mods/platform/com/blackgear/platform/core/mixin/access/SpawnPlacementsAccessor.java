package com.blackgear.platform.core.mixin.access;

import java.util.Map;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Data;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpawnPlacements.class)
public interface SpawnPlacementsAccessor {
   @Accessor("DATA_BY_TYPE")
   static Map<EntityType<?>, Data> getDATA_BY_TYPE() {
      throw new UnsupportedOperationException();
   }
}
