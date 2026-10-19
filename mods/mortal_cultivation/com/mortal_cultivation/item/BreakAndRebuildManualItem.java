package com.mortal_cultivation.item;

import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class BreakAndRebuildManualItem extends Item {
   public BreakAndRebuildManualItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (!level.isClientSide) {
         Races raceData = RaceAPI.getRaceFrom(player);
         boolean isWastelander = raceData.getRace().map(instance -> instance.getRace() == CultivationRaces.WASTELANDER_RACE.get()).orElse(false);
         if (!isWastelander) {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.item.break_and_rebuild_manual.not_suitable").withStyle(ChatFormatting.RED));
            return InteractionResultHolder.fail(itemStack);
         }

         raceData.setRace((ManasRace)TensuraRaces.HUMAN.get(), false);
         raceData.markDirty();
         player.sendSystemMessage(
            Component.translatable("mortal_cultivation.item.break_and_rebuild_manual.success")
               .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
         );
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.2F);
         if (player.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 40; i++) {
               double offsetX = (player.getRandom().nextDouble() - 0.5) * 2.0;
               double offsetY = player.getRandom().nextDouble() * 2.0;
               double offsetZ = (player.getRandom().nextDouble() - 0.5) * 2.0;
               serverLevel.sendParticles(
                  ParticleTypes.ENCHANT, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.1
               );
            }

            for (int i = 0; i < 20; i++) {
               double offsetX = (player.getRandom().nextDouble() - 0.5) * 1.5;
               double offsetY = player.getRandom().nextDouble() * 1.5;
               double offsetZ = (player.getRandom().nextDouble() - 0.5) * 1.5;
               serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.05);
            }
         }

         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         return InteractionResultHolder.success(itemStack);
      } else {
         return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("mortal_cultivation.item.break_and_rebuild_manual.tooltip1").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("mortal_cultivation.item.break_and_rebuild_manual.tooltip2").withStyle(ChatFormatting.DARK_PURPLE));
   }
}
