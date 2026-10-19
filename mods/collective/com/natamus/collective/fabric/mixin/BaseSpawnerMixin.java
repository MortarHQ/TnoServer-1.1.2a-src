package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveSpawnEvents;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1917;
import net.minecraft.class_1937;
import net.minecraft.class_1952;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_3218;
import net.minecraft.class_3730;
import net.minecraft.class_5819;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_1917.class, priority = 1001)
public abstract class BaseSpawnerMixin {
   @Shadow
   protected abstract void method_8282(class_1937 var1, class_2338 var2);

   @Inject(
      method = "serverTick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z"),
      cancellable = true,
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void BaseSpawner_serverTickprivate(
      class_3218 serverLevel,
      class_2338 blockPos,
      CallbackInfo ci,
      boolean bl,
      class_5819 randomSource,
      class_1952 spawnData,
      int i,
      class_2487 compoundTag,
      Optional optional,
      class_2499 listTag,
      int j,
      double d,
      double e,
      double f,
      class_2338 blockPos2,
      class_1297 entity,
      int k,
      class_1308 mob
   ) {
      mob.method_5780("collective.fromspawner");
      if (!((CollectiveSpawnEvents.Mob_Check_Spawn)CollectiveSpawnEvents.MOB_CHECK_SPAWN.invoker())
         .onMobCheckSpawn(mob, serverLevel, blockPos, class_3730.field_16469)) {
         ci.cancel();
         this.method_8282(serverLevel, blockPos);
      }
   }
}
