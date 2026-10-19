package com.mortal_cultivation.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.crafting.RecipeType;

public class EssenceCharcoalItem extends Item {
   private final int burnTime;

   public EssenceCharcoalItem(Properties properties, int burnTime) {
      super(properties);
      this.burnTime = burnTime;
   }

   public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
      return this.burnTime;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      String tooltipKey = "mortal_cultivation.item." + this.getRegistryName().getPath() + ".tooltip";
      tooltip.add(Component.translatable(tooltipKey));
      super.appendHoverText(stack, context, tooltip, flag);
   }

   private ResourceLocation getRegistryName() {
      return BuiltInRegistries.ITEM.getKey(this);
   }
}
