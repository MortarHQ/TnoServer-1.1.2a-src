package com.github.wal_bos.moreskills.mixin.compat;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.github.hvnbael.trnightmare.titleapi.TitleAPI", remap = false)
public abstract class NightmareTitleFeldwayMixin {
   @Inject(
      method = "neutralizes(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/LivingEntity;)Z",
      at = @At("HEAD"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tensuramoreskills$feldwayCannotBeNeutralized(ServerPlayer player, LivingEntity potentialAttacker, CallbackInfoReturnable<Boolean> cir) {
      if (potentialAttacker instanceof FeldwayEntity || potentialAttacker instanceof FeldwayAwakenedEntity) {
         cir.setReturnValue(false);
      }
   }
}
