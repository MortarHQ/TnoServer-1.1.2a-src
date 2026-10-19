package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.static_utilities.ParticleUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;

public class LichKillCounter {
   private final LichConfig.SummonMechanic config;
   private final List<EntityType<?>> countedEntities;

   public LichKillCounter(LichConfig.SummonMechanic config) {
      this.config = config;
      this.countedEntities = config.entitiesThatCountToSummonCounter != null
         ? config.entitiesThatCountToSummonCounter
            .stream()
            .map(string -> (EntityType)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(string)))
            .collect(Collectors.toList())
         : List.of();
   }

   public void afterKilledOtherEntity(Entity entity, LivingEntity killedEntity) {
      if (entity instanceof ServerPlayer && this.countedEntities.contains(killedEntity.getType())) {
         int entitiesKilled = this.getUndeadKilled((ServerPlayer)entity);
         if (entitiesKilled > 0 && entitiesKilled % this.config.numEntitiesKilledToDropSoulStar == 0) {
            ParticleUtils.spawnParticle(
               ((ServerPlayer)entity).serverLevel(),
               (ParticleOptions)BMDParticles.SOUL_FLAME.get(),
               killedEntity.position().add(VecUtils.yAxis),
               VecUtils.unit,
               15,
               0.0
            );
            killedEntity.spawnAtLocation((ItemLike)BMDItems.SOUL_STAR.get());
         }
      }
   }

   private int getUndeadKilled(ServerPlayer entity) {
      return this.countedEntities.stream().mapToInt(entityType -> entity.getStats().getValue(Stats.ENTITY_KILLED.get(entityType))).sum();
   }
}
