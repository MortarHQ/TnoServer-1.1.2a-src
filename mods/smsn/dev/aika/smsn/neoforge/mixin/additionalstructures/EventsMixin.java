package dev.aika.smsn.neoforge.mixin.additionalstructures;

import dev.aika.smsn.SMSN;
import java.net.URL;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xxrexraptorxx.additionalstructures.utils.Events;

@Mixin(value = Events.class, remap = false)
public abstract class EventsMixin {
   @Inject(method = "SupporterCheck(Ljava/net/URL;Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"), cancellable = true)
   private static void SupporterCheck(URL url, Player player, CallbackInfoReturnable<Boolean> cir) {
      if (SMSN.CONFIG.isAdditionalStructuresSupporterCheck()) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "SupporterRewards(Lnet/neoforged/neoforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;)V", at = @At("HEAD"), cancellable = true)
   private static void SupporterRewards(PlayerLoggedInEvent event, CallbackInfo ci) {
      if (SMSN.CONFIG.isAdditionalStructuresSupporterCheck()) {
         ci.cancel();
      }
   }
}
