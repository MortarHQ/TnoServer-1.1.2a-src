package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.event.AnvilHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
   @Inject(method = "handleContainerContent", at = @At("HEAD"))
   private void onInventory(ClientboundContainerSetContentPacket var1, CallbackInfo var2) {
      if (Vanilla.INSTANCE.screen() instanceof AnvilScreen) {
         synchronized (AnvilHandler.INSTANCE.getSync()) {
            AnvilHandler.INSTANCE.setTicksAfterLastPacket(0);
         }

         Log.INSTANCE.trace("Received Inventory Packet");
      }
   }
}
