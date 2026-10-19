package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class PhainonDawnmaker {
   private PhainonDawnmaker() {
   }

   public static void toggle(LivingEntity entity, ManasSkillInstance instance) {
      if (instance.getOrCreateTag().getBoolean("PhainonDawnmakerActive")) {
         restore(entity, instance);
         entity.sendSystemMessage(Component.literal("Dawnmaker released.").withStyle(ChatFormatting.GRAY));
      } else {
         create(entity, instance);
      }
   }

   public static void create(LivingEntity entity, ManasSkillInstance instance) {
      ItemStack held = entity.getMainHandItem();
      if (!held.isEmpty()) {
         Tag saved = held.save(entity.registryAccess());
         instance.getOrCreateTag().put("PhainonDawnmakerOriginal", saved);
      }

      int cores = PhainonProgress.get(instance).getCoreFlameCount();
      ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
      sword.set(DataComponents.CUSTOM_NAME, Component.literal("Dawnmaker [" + cores * 50 + "]").withStyle(ChatFormatting.GOLD));
      entity.setItemInHand(InteractionHand.MAIN_HAND, sword);
      instance.getOrCreateTag().putBoolean("PhainonDawnmakerActive", true);
      entity.sendSystemMessage(Component.literal("Dawnmaker forms in your hand.").withStyle(ChatFormatting.GOLD));
   }

   public static void restore(LivingEntity entity, ManasSkillInstance instance) {
      if (instance.getOrCreateTag().getBoolean("PhainonDawnmakerActive")) {
         if (instance.getOrCreateTag().contains("PhainonDawnmakerOriginal")) {
            ItemStack original = ItemStack.parseOptional(entity.registryAccess(), instance.getOrCreateTag().getCompound("PhainonDawnmakerOriginal"));
            entity.setItemInHand(InteractionHand.MAIN_HAND, original);
         } else {
            entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
         }

         instance.getOrCreateTag().remove("PhainonDawnmakerOriginal");
         instance.getOrCreateTag().putBoolean("PhainonDawnmakerActive", false);
      }
   }
}
