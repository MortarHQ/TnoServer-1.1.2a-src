package com.faboslav.friendsandfoes.common.modcompat;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ModCompat {
   default EnumSet<ModCompat.Type> compatTypes() {
      return EnumSet.noneOf(ModCompat.Type.class);
   }

   @Nullable
   default ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
      return null;
   }

   enum Type {
      CUSTOM_EQUIPMENT_SLOTS;
   }
}
