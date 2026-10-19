package com.bobmowzie.mowziesmobs.server.util;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnchantmentUtils {
   public static int getLevel(@NotNull ResourceKey<Enchantment> enchantment, @NotNull LivingEntity entity) {
      return entity.level()
         .registryAccess()
         .registryOrThrow(Registries.ENCHANTMENT)
         .getHolder(enchantment)
         .map(reference -> EnchantmentHelper.getEnchantmentLevel(reference, entity))
         .orElse(0);
   }

   public static int getLevel(@NotNull ResourceKey<Enchantment> enchantment, @NotNull Level level, @NotNull ItemStack stack) {
      return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(enchantment).<Integer>map(stack::getEnchantmentLevel).orElse(0);
   }

   @Nullable
   public static Reference<Enchantment> getHolder(ResourceKey<Enchantment> enchantment) {
      RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
      return lookup == null ? null : lookup.getOrThrow(enchantment);
   }
}
