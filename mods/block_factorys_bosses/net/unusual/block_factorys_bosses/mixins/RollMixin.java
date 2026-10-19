package net.unusual.block_factorys_bosses.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class RollMixin {
   @Inject(method = "setTicksFrozen", at = @At("HEAD"), cancellable = true)
   private void freshNeverFrozen(int ticksFrozen, CallbackInfo ci) {
      Entity var4 = (Entity)this;
      if (var4 instanceof Player player && RollAttachment.fromPlayer(player).isInvulnerable()) {
         ci.cancel();
      }
   }
}
