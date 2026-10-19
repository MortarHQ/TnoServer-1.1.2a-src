package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.YigdCompat;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.b1n_ry.yigd.components.GraveComponent", remap = false)
public abstract class YigdLegacyGraveClaimMixin {
   @Inject(
      method = "applyToPlayer(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;ZLjava/util/function/Predicate;)V",
      at = @At("HEAD"),
      remap = false,
      require = 0
   )
   private void tensuraTno$repairLegacyInventoryBeforeFilteredClaim(
      ServerPlayer player, ServerLevel level, Vec3 pos, boolean isGraveOwner, Predicate<?> itemFilter, CallbackInfo ci
   ) {
      YigdCompat.repairLegacyGraveInventory(this, player);
   }
}
