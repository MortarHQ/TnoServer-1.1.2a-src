package com.mortal_cultivation.item;

import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class SizeTransformationManualItem extends Item {
   public SizeTransformationManualItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (level.isClientSide) {
         return InteractionResultHolder.sidedSuccess(stack, true);
      }

      if (SkillUtils.hasSkill(player, (ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get())) {
         return InteractionResultHolder.success(stack);
      }

      if (!SkillAPI.getSkillsFrom(player).learnSkill((ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get())) {
         return InteractionResultHolder.fail(stack);
      }

      if (!player.getAbilities().instabuild) {
         stack.shrink(1);
      }

      return InteractionResultHolder.success(stack);
   }
}
