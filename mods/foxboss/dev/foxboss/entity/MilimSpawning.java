package dev.foxboss.entity;

import dev.foxboss.FoxBossConfig;
import dev.foxboss.registry.FoxEntities;
import io.github.manasmods.tensura.config.entity.SpawnRateConfig;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;

@EventBusSubscriber(modid = "foxboss", bus = Bus.MOD)
public final class MilimSpawning {
   @SubscribeEvent
   public static void register(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)FoxEntities.MILIM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TensuraTamableEntity::checkTensuraMobSpawnRules,
         Operation.REPLACE
      );
   }

   public static boolean checkSpawnChance(LevelAccessor level, MobSpawnType reason) {
      if (reason != MobSpawnType.NATURAL && reason != MobSpawnType.CHUNK_GENERATION) {
         return true;
      }

      SpawnRateConfig config = TensuraEntityTypes.CONFIG;
      return TensuraEntityTypes.rollSpawn(config.SpawnChance.otherworlder, level, reason)
         && rollVariant(
            (FoxBossConfig.SpawnReference)FoxBossConfig.NATURAL_SPAWN_REFERENCE.get(),
            config.SpecialVariant.shizuChance,
            config.SpecialVariant.hinataChance,
            level.getRandom()
         );
   }

   static boolean rollVariant(FoxBossConfig.SpawnReference reference, int shizu, int hinata, RandomSource random) {
      boolean shizuRoll = TensuraEntityTypes.rollChance(shizu, random);
      return reference == FoxBossConfig.SpawnReference.SHIZU ? shizuRoll : !shizuRoll && TensuraEntityTypes.rollChance(hinata, random);
   }

   private MilimSpawning() {
   }
}
