package io.github.Memoires.mysticism.mixin;

import io.github.Memoires.mysticism.handler.AttributeHandler;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReincarnationMenu.class)
public class MixinReincarnationMenu {
   @Inject(method = "checkForFirstLogin(Lnet/minecraft/server/level/ServerPlayer;)Z", at = @At("HEAD"))
   private static void mysticism$checkForFirstLogin(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
      Races races = RaceAPI.getRaceFrom(player);
      if (races.getRace().isEmpty()) {
         ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
         int newSoulEnergy = AttributeHandler.getWeightedSoulEnergy();
         if (player.level().getGameRules().getBoolean(TensuraGameRules.RIMURU_MODE)) {
            newSoulEnergy = 3000000;
         }

         Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(newSoulEnergy);
         soulEnergyStorage.setSoulEnergy(newSoulEnergy);
         soulEnergyStorage.markDirty();
         player.sendSystemMessage(
            Component.translatable("mysticism.soulEnergy.acquire_start", new Object[]{AttributeHandler.getShortenedNumber(newSoulEnergy)})
               .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
         );
      }
   }

   @Redirect(
      method = "grantedUniqueSkill(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/server/level/ServerLevel;Lio/github/manasmods/manascore/skill/api/ManasSkill;Z)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V")
   )
   private static void mysticism$grantedUniqueSkill(CompoundTag tag, String key, boolean value) {
      tag.putBoolean(key, value);
      if ("NoMagiculeCost".equals(key)) {
         tag.putBoolean("GiftedInstance", true);
      }
   }
}
