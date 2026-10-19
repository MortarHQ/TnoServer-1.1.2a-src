package com.mortal_cultivation.item;

import com.mortal_cultivation.ability.skill.unique.AlchemyProdigySkill;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class TheSupremeAlchemyCanonItem extends Item {
   public TheSupremeAlchemyCanonItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (!level.isClientSide) {
         try {
            boolean hasSkill = SkillUtils.hasSkill(player, (ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get());
            if (!hasSkill) {
               boolean learned = SkillAPI.getSkillsFrom(player).learnSkill((ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get());
               if (learned) {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.learned"), true);
                  if (!player.getAbilities().instabuild) {
                     itemStack.shrink(1);
                  }

                  return InteractionResultHolder.success(itemStack);
               } else {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.learn_failed"), true);
                  return InteractionResultHolder.fail(itemStack);
               }
            } else {
               ManasSkillInstance skillInstance = SkillUtils.getSkillOrNull(player, (ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get());
               if (skillInstance != null) {
                  try {
                     int currentMastery = (int)skillInstance.getMastery();
                     int maxMastery = ((AlchemyProdigySkill)CultivationSkills.ALCHEMY_PRODIGY.get()).getMaxMastery();
                     int masteryIncrease = (int)(maxMastery * 0.2);
                     int newMastery = Math.min(maxMastery, currentMastery + masteryIncrease);
                     skillInstance.setMastery(newMastery);
                     skillInstance.markDirty();
                     SkillAPI.getSkillsFrom(player).updateSkill(skillInstance, true);
                     if (newMastery >= maxMastery) {
                        skillInstance.onSkillMastered(player);
                        player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.mastered"), false);
                     } else {
                        player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.progress"), true);
                     }

                     if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                     }

                     return InteractionResultHolder.success(itemStack);
                  } catch (Exception e) {
                     player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.progress"), true);
                     if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                     }

                     return InteractionResultHolder.success(itemStack);
                  }
               } else {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.instance_failed"), true);
                  return InteractionResultHolder.fail(itemStack);
               }
            }
         } catch (Exception e) {
            player.displayClientMessage(
               Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.use_failed", new Object[]{e.getMessage()}), true
            );
            return InteractionResultHolder.fail(itemStack);
         }
      } else {
         return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.tooltip1"));
      tooltip.add(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.tooltip2"));
      tooltip.add(Component.empty());
      tooltip.add(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.usage"));
      tooltip.add(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.usage_learn"));
      tooltip.add(Component.translatable("mortal_cultivation.item.the_supreme_alchemy_canon.usage_mastery"));
   }
}
