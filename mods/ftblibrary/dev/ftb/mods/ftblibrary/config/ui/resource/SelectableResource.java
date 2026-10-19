package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.architectury.fluid.FluidStack;
import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SelectableResource<T> {
   static SelectableResource<ItemStack> item(ItemStack stack) {
      return new ItemStackResource(stack);
   }

   static SelectableResource<FluidStack> fluid(FluidStack stack) {
      return new FluidStackResource(stack);
   }

   T resource();

   long getCount();

   void setCount(int var1);

   default boolean isEmpty() {
      return this.getCount() == 0L;
   }

   Component getName();

   Icon getIcon();

   SelectableResource<T> copyWithCount(long var1);

   @Nullable
   default CompoundTag getComponentsTag() {
      return null;
   }

   default void applyComponentsTag(CompoundTag tag) {
   }
}
