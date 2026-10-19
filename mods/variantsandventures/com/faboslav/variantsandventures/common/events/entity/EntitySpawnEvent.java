package com.faboslav.variantsandventures.common.events.entity;

import com.faboslav.variantsandventures.common.events.base.CancellableEventHandler;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public record EntitySpawnEvent(Mob entity, LevelAccessor worldAccess, boolean isBaby, MobSpawnType spawnReason) {
   public static final CancellableEventHandler<EntitySpawnEvent> EVENT = new CancellableEventHandler<>();
}
