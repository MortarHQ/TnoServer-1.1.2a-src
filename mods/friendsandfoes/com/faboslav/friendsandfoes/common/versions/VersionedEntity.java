package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class VersionedEntity {
   public static EquipmentSlot getEquipmentSlotForItem(InteractionHand hand) {
      return LivingEntity.getSlotForHand(hand);
   }

   public static InteractionResult success(Entity entity) {
      return InteractionResult.sidedSuccess(entity.level().isClientSide());
   }

   public static boolean hurt(Entity entity, DamageSource damageSource, float amount) {
      boolean hurtResult = false;
      return entity.hurt(damageSource, amount);
   }

   public static void moveTo(Entity entity, double x, double y, double z) {
      entity.moveTo(x, y, z);
   }

   public static void moveTo(Entity entity, double x, double y, double z, float f, float g) {
      entity.moveTo(x, y, z, f, g);
   }

   public static void moveTo(Entity entity, BlockPos blockPos, float f, float g) {
      entity.moveTo(blockPos, f, g);
   }

   public static ItemEntity spawnAtLocation(Entity entity, ItemLike stack) {
      return spawnAtLocation(entity, stack, 0.0F);
   }

   public static ItemEntity spawnAtLocation(Entity entity, ItemLike itemLike, float yOffset) {
      return spawnAtLocation(entity, new ItemStack(itemLike), yOffset);
   }

   public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack) {
      return spawnAtLocation(entity, stack, 0.0F);
   }

   public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack, float yOffset) {
      return entity.spawnAtLocation(stack, yOffset);
   }
}
