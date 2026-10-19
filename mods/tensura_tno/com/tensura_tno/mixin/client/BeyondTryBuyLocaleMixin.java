package com.tensura_tno.mixin.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.trbeyond.network.packets.c2s.TryBuyC2SPacket", remap = false)
public class BeyondTryBuyLocaleMixin {
   private static final String PREFIX = "Max copies reached for ";
   private static final String CONVERTED_TO = ". Converted to ";
   private static final String SOULS_SUFFIX = " souls.";

   @Redirect(
      method = "lambda$processBuy$1",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;sendSystemMessage(Lnet/minecraft/network/chat/Component;)V", ordinal = 0),
      remap = false,
      require = 0
   )
   private static void tensura_tno$localizeMaxCopies(ServerPlayer player, Component original) {
      String text = original.getString();
      int nameEnd = text.indexOf(". Converted to ");
      int soulsStart = nameEnd > 0 ? nameEnd + ". Converted to ".length() : -1;
      int soulsEnd = text.lastIndexOf(" souls.");
      if (nameEnd > "Max copies reached for ".length() && soulsEnd > soulsStart) {
         String heroName = text.substring("Max copies reached for ".length(), nameEnd);
         String soulsCount = text.substring(soulsStart, soulsEnd);
         player.sendSystemMessage(
            Component.translatable("tensura_tno.beyond.gacha.max_copies_converted", new Object[]{heroName, soulsCount}).withStyle(ChatFormatting.GOLD)
         );
      } else {
         player.sendSystemMessage(original);
      }
   }
}
