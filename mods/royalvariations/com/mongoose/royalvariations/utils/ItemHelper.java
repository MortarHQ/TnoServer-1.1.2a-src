package com.mongoose.royalvariations.utils;

import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemHelper {
   public static <T extends LivingEntity> void hurtAndBreak(ItemStack itemStack, int pAmount, T pEntity) {
      if (pEntity.level() instanceof ServerLevel serverlevel) {
         itemStack.hurtAndBreak(pAmount, serverlevel, pEntity, p_348383_ -> pEntity.onEquippedItemBroken(p_348383_, EquipmentSlot.MAINHAND));
      }
   }

   public static boolean armorSet(LivingEntity living, ArmorMaterial material) {
      int i = 0;
      if (living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem helmet && helmet.getMaterial().value() == material) {
         for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.getType() == Type.HUMANOID_ARMOR
               && living.getItemBySlot(equipmentSlot).getItem() instanceof ArmorItem armorItem
               && armorItem.getMaterial().value() == material) {
               i++;
            }
         }
      }

      return i >= 4;
   }

   public static ItemEntity itemEntityDrop(LivingEntity livingEntity, ItemStack itemStack) {
      return new ItemEntity(livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), itemStack);
   }

   public static void addItemEntity(Level level, BlockPos blockPos, ItemStack itemStack) {
      double d0 = level.random.nextFloat() * 0.5F + 0.25;
      double d1 = level.random.nextFloat() * 0.5F + 0.25;
      double d2 = level.random.nextFloat() * 0.5F + 0.25;
      ItemEntity itementity = new ItemEntity(level, blockPos.getX() + d0, blockPos.getY() + d1, blockPos.getZ() + d2, itemStack);
      itementity.setDefaultPickUpDelay();
      level.addFreshEntity(itementity);
   }

   public static Component getShiftInfoTooltip() {
      return Component.translatable("info.royalvariations.item_info");
   }

   public static void addOnShift(List<Component> tooltip, Runnable lambda) {
      if (Screen.hasShiftDown()) {
         lambda.run();
      } else {
         tooltip.add(getShiftInfoTooltip());
      }
   }
}
