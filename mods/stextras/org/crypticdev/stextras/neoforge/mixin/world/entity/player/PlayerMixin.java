package org.crypticdev.stextras.neoforge.mixin.world.entity.player;

import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.support.PlayerPhasingHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
   @Redirect(method = {"tick()V", "updatePlayerPose()V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"))
   private boolean stextras$pretendSpectatorWhenPhasing(Player self) {
      return PlayerPhasingHooks.pretendSpectatorWhenPhasing(self);
   }
}
