package com.finderfeed.raids_enhanced;

import com.finderfeed.raids_enhanced.content.entities.player_blimp.PlayerBlimpEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class REClientMixinHandler {
   public static void setPassengers(ClientLevel level, ClientboundSetPassengersPacket packet, CallbackInfo ci) {
      int vehicle = packet.getVehicle();
      if (level.getEntity(vehicle) instanceof PlayerBlimpEntity playerBlimp) {
         Minecraft minecraft = Minecraft.getInstance();
         MutableComponent component = Component.translatable("raidsenhanced.mount").withStyle(ChatFormatting.RED);
         minecraft.gui.setOverlayMessage(component, false);
         minecraft.getNarrator().sayNow(component);
      }
   }
}
