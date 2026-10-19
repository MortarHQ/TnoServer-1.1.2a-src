package com.github.wal_bos.moreskills.handler.asmoday;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public final class AsmodaySpaceEmbodiment implements AsmodayAttack {
   public static final double PLAYER_COST = 2500.0;
   public static final double DIMENSION_COST = 8000.0;
   public static final int PLAYER_COOLDOWN = 4;
   public static final int DIMENSION_COOLDOWN = 10;

   @Override
   public String modeId() {
      return "asmoday.space_embodiment";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return entity.isShiftKeyDown() ? 8000.0 : 2500.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (!EnergyHelper.isOutOfEnergy(entity, this.magiculeCost(entity, instance), 0.0)) {
            if (entity.isShiftKeyDown()) {
               AsmodayChatTeleportHandler.requestDimension(player, instance, mode);
               player.sendSystemMessage(
                  Component.literal(
                        "Space Embodiment opened. Type a dimension id in chat. Examples: minecraft:overworld, minecraft:the_nether, minecraft:the_end. Type cancel to close."
                     )
                     .withStyle(ChatFormatting.LIGHT_PURPLE)
               );
            } else {
               AsmodayChatTeleportHandler.requestPlayer(player, instance, mode);
               player.sendSystemMessage(
                  Component.literal("Space Embodiment opened. Type an online player's name in chat. Type list to see valid targets. Type cancel to close.")
                     .withStyle(ChatFormatting.LIGHT_PURPLE)
               );
            }

            player.swing(InteractionHand.MAIN_HAND, true);
            player.level()
               .playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.0F, 0.85F);
            AsmodayFx.spawnSpaceOpen(player);
         }
      }
   }
}
