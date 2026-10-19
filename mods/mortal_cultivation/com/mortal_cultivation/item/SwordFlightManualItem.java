package com.mortal_cultivation.item;

import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class SwordFlightManualItem extends Item {
   public SwordFlightManualItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (level.isClientSide) {
         return InteractionResultHolder.sidedSuccess(itemStack, true);
      }

      try {
         if (SkillUtils.hasSkill(player, (ManasSkill)CultivationSkills.SWORD_FLIGHT.get())) {
            player.displayClientMessage(Component.translatable("mortal_cultivation.item.sword_flight_manual.already_known"), true);
            return InteractionResultHolder.success(itemStack);
         }

         boolean learned = SkillAPI.getSkillsFrom(player).learnSkill((ManasSkill)CultivationSkills.SWORD_FLIGHT.get());
         if (!learned) {
            player.displayClientMessage(Component.translatable("mortal_cultivation.item.sword_flight_manual.learn_failed"), true);
            return InteractionResultHolder.fail(itemStack);
         }

         player.displayClientMessage(Component.translatable("mortal_cultivation.item.sword_flight_manual.learned"), true);
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         return InteractionResultHolder.success(itemStack);
      } catch (Exception exception) {
         player.displayClientMessage(
            Component.translatable("mortal_cultivation.item.sword_flight_manual.use_failed", new Object[]{exception.getMessage()}), true
         );
         return InteractionResultHolder.fail(itemStack);
      }
   }
}
