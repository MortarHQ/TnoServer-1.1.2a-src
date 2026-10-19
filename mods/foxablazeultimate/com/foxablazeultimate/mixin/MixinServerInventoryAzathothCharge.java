package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.azathoth.AzathothCombat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerInventoryAzathothCharge {
   @Shadow
   public ServerPlayer player;

   @Inject(
      method = {
            "handleSetCarriedItem(Lnet/minecraft/network/protocol/game/ServerboundSetCarriedItemPacket;)V",
            "handleContainerClick(Lnet/minecraft/network/protocol/game/ServerboundContainerClickPacket;)V",
            "handlePlayerAction(Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket;)V",
            "handleSetCreativeModeSlot(Lnet/minecraft/network/protocol/game/ServerboundSetCreativeModeSlotPacket;)V"
      },
      at = @At("RETURN")
   )
   private void foxablazeultimate$cancelChargeAfterInventoryChange(CallbackInfo callback) {
      AzathothCombat.cancelChargeIfItemChanged(this.player);
   }
}
