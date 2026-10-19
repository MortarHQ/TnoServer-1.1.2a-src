package io.github.Memoires.mysticism.mixin.item;

import io.github.Memoires.mysticism.command.MysticismCommands;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResetScrollItem.class)
public class MixinResetScrollItem {
   @Inject(method = "resetRace(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("RETURN"))
   private static void mysticism$resetRace(ServerPlayer player, CallbackInfo ci) {
      MysticismStorages.resetElement(player);
      MysticismStorages.resetPlayerData(player);
      player.manasCore$sync(player);
   }

   @Inject(method = "resetEverything(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("HEAD"))
   private static void mysticism$resetEverything(ServerPlayer player, CallbackInfo ci) {
      if (ResetScrollItem.isFullReset(player)) {
         ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
         double currentSoulEnergy = soulEnergyStorage.getSoulEnergy();
         double maxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
         int newSoulEnergy = ThreadLocalRandom.current().nextInt(500000, 1000000);
         double finalMaxSoulEnergy = newSoulEnergy + maxSoulEnergy;
         double finalCurrentSoulEnergy = newSoulEnergy + currentSoulEnergy;
         Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(finalMaxSoulEnergy);
         soulEnergyStorage.setSoulEnergy(finalCurrentSoulEnergy);
         soulEnergyStorage.markDirty();
         player.sendSystemMessage(
            Component.translatable("mysticism.soulEnergy.acquire_full_reset", new Object[]{MysticismCommands.getShortenedNumber(newSoulEnergy)})
               .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
         );
      }

      ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
      double maxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
      soulEnergyStorage.setSoulEnergy(maxSoulEnergy);
      soulEnergyStorage.markDirty();
      MysticismStorages.resetElement(player);
      MysticismStorages.resetPlayerData(player);
      player.manasCore$sync(player);
   }

   @Inject(method = "resetSkill(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("HEAD"))
   private static void mysticism$resetSkill(ServerPlayer player, CallbackInfo ci) {
      ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
      double maxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
      soulEnergyStorage.setSoulEnergy(maxSoulEnergy);
      soulEnergyStorage.markDirty();
      player.manasCore$sync(player);
   }
}
