package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

public record AddSpawnBiomeModificationsEvent(AddSpawnBiomeModificationsEvent.Registrar registrar) {
   public static final EventHandler<AddSpawnBiomeModificationsEvent> EVENT = new EventHandler<>();

   public void add(TagKey<Biome> tag, MobCategory spawnGroup, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize) {
      this.registrar.add(tag, spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
   }

   @FunctionalInterface
   public interface Registrar {
      void add(TagKey<Biome> var1, MobCategory var2, EntityType<?> var3, int var4, int var5, int var6);
   }
}
