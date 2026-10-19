package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveSpawnEvents;
import net.minecraft.class_1308;
import net.minecraft.class_1311;
import net.minecraft.class_1948;
import net.minecraft.class_2338;
import net.minecraft.class_2791;
import net.minecraft.class_3218;
import net.minecraft.class_3730;
import net.minecraft.class_1948.class_5259;
import net.minecraft.class_1948.class_5261;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = class_1948.class, priority = 1001)
public class NaturalSpawnerMixin {
   @ModifyVariable(
      method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/world/level/NaturalSpawner;getMobForSpawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/EntityType;)Lnet/minecraft/world/entity/Mob;",
         ordinal = 0
      )
   )
   private static class_1308 NaturalSpawner_spawnCategoryForPosition(
      class_1308 mob,
      class_1311 mobCategory,
      class_3218 serverLevel,
      class_2791 chunkAccess,
      class_2338 blockPos,
      class_5261 spawnPredicate,
      class_5259 afterSpawnCallback
   ) {
      if (mob != null) {
         mob.method_5814(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
      }

      if (!((CollectiveSpawnEvents.Mob_Check_Spawn)CollectiveSpawnEvents.MOB_CHECK_SPAWN.invoker())
         .onMobCheckSpawn(mob, serverLevel, null, class_3730.field_16459)) {
         mob = null;
      }

      return mob;
   }
}
