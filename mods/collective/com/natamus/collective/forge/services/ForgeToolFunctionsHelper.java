package com.natamus.collective.forge.services;

import com.natamus.collective_common_forge.services.helpers.ToolFunctionsHelper;
import java.util.Set;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ForgeToolFunctionsHelper implements ToolFunctionsHelper {
   public static final ToolAction LIGHT_CAMPFIRE = ToolAction.get("light_campfire");

   @Override
   public boolean isTool(ItemStack itemstack) {
      return this.isPickaxe(itemstack) || this.isAxe(itemstack) || this.isShovel(itemstack) || this.isHoe(itemstack) || this.isShears(itemstack);
   }

   @Override
   public boolean isSword(ItemStack itemStack) {
      return itemStack.getItem() instanceof SwordItem || itemStack.is(ItemTags.SWORDS) || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_SWORD_ACTIONS);
   }

   @Override
   public boolean isShield(ItemStack itemStack) {
      return itemStack.getItem() instanceof ShieldItem || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_SHIELD_ACTIONS);
   }

   @Override
   public boolean isPickaxe(ItemStack itemStack) {
      return itemStack.getItem() instanceof PickaxeItem
         || itemStack.is(ItemTags.PICKAXES)
         || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_PICKAXE_ACTIONS);
   }

   @Override
   public boolean isAxe(ItemStack itemStack) {
      return itemStack.getItem() instanceof AxeItem || itemStack.is(ItemTags.AXES) || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_AXE_ACTIONS);
   }

   @Override
   public boolean isShovel(ItemStack itemStack) {
      return itemStack.getItem() instanceof ShovelItem
         || itemStack.is(ItemTags.SHOVELS)
         || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_SHOVEL_ACTIONS);
   }

   @Override
   public boolean isHoe(ItemStack itemStack) {
      return itemStack.getItem() instanceof HoeItem || itemStack.is(ItemTags.HOES) || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_HOE_ACTIONS);
   }

   @Override
   public boolean isShears(ItemStack itemStack) {
      return itemStack.getItem() instanceof ShearsItem || canPerformOneOfActions(itemStack, ToolActions.DEFAULT_SHEARS_ACTIONS);
   }

   @Override
   public boolean isFlintAndSteel(ItemStack itemStack) {
      return itemStack.getItem() instanceof FlintAndSteelItem || itemStack.canPerformAction(LIGHT_CAMPFIRE);
   }

   private static boolean canPerformOneOfActions(ItemStack itemStack, Set<ToolAction> toolActions) {
      for (ToolAction toolAction : toolActions) {
         if (itemStack.canPerformAction(toolAction)) {
            return true;
         }
      }

      return false;
   }
}
