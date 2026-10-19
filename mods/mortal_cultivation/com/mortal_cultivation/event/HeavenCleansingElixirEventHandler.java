package com.mortal_cultivation.event;

import com.mojang.logging.LogUtils;
import com.mortal_cultivation.registry.item.CultivationItems;
import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import org.slf4j.Logger;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class HeavenCleansingElixirEventHandler {
   private static final Logger LOGGER = LogUtils.getLogger();

   @SubscribeEvent
   public static void onItemUseFinish(Finish event) {
      if (event.getEntity() instanceof Player player) {
         ItemStack usedItem = event.getItem();
         if (usedItem.is((Item)CultivationItems.HEAVEN_CLEANSING_ELIXIR.get())) {
            Races raceData = RaceAPI.getRaceFrom(player);
            boolean isWastelander = raceData.getRace().map(instance -> instance.getRace() == CultivationRaces.WASTELANDER_RACE.get()).orElse(false);
            if (isWastelander) {
               transformToHuman(player, raceData);
            }
         }
      }
   }

   private static void transformToHuman(Player player, Races raceData) {
      raceData.setRace((ManasRace)TensuraRaces.HUMAN.get(), false);
      raceData.markDirty();
      Component message = Component.translatable("mortal_cultivation.heaven_cleansing_elixir.transformation")
         .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});
      player.sendSystemMessage(message);
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
      if (player.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 50; i++) {
            double offsetX = (player.getRandom().nextDouble() - 0.5) * 2.0;
            double offsetY = player.getRandom().nextDouble() * 2.0;
            double offsetZ = (player.getRandom().nextDouble() - 0.5) * 2.0;
            serverLevel.sendParticles(ParticleTypes.ENCHANT, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.1);
         }

         for (int i = 0; i < 30; i++) {
            double offsetX = (player.getRandom().nextDouble() - 0.5) * 1.5;
            double offsetY = player.getRandom().nextDouble() * 1.5;
            double offsetZ = (player.getRandom().nextDouble() - 0.5) * 1.5;
            serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.05);
         }
      }

      LOGGER.info("Player {} transformed from Wastelander to Human using Heaven Cleansing Elixir", player.getName().getString());
   }
}
