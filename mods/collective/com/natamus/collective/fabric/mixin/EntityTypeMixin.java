package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveSpawnEvents;
import java.util.function.Consumer;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1308;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_3730;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_1299.class, priority = 1001)
public class EntityTypeMixin<T extends class_1297> {
   @Inject(
      method = "spawn(Lnet/minecraft/server/level/ServerLevel;Ljava/util/function/Consumer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"),
      locals = LocalCapture.CAPTURE_FAILSOFT,
      cancellable = true
   )
   public void EntityType_spawn(
      class_3218 serverLevel,
      Consumer<T> consumer,
      class_2338 blockPos,
      class_3730 mobSpawnType,
      boolean bl,
      boolean bl2,
      CallbackInfoReturnable<T> cir,
      class_1297 entity
   ) {
      if (entity instanceof class_1308
         && !((CollectiveSpawnEvents.Mob_Special_Spawn)CollectiveSpawnEvents.MOB_SPECIAL_SPAWN.invoker())
            .onMobSpecialSpawn((class_1308)entity, serverLevel, blockPos, null, mobSpawnType)) {
         cir.setReturnValue(null);
      }
   }
}
