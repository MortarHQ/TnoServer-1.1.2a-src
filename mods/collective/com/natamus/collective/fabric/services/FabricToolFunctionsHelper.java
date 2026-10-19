package com.natamus.collective.fabric.services;

import com.natamus.collective_common_fabric.services.helpers.ToolFunctionsHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.class_1743;
import net.minecraft.class_1786;
import net.minecraft.class_1794;
import net.minecraft.class_1799;
import net.minecraft.class_1810;
import net.minecraft.class_1819;
import net.minecraft.class_1820;
import net.minecraft.class_1821;
import net.minecraft.class_1829;
import net.minecraft.class_3489;

public class FabricToolFunctionsHelper implements ToolFunctionsHelper {
   @Override
   public boolean isTool(class_1799 itemstack) {
      return this.isPickaxe(itemstack) || this.isAxe(itemstack) || this.isShovel(itemstack) || this.isHoe(itemstack) || this.isShears(itemstack);
   }

   @Override
   public boolean isSword(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1829 || itemStack.method_31573(class_3489.field_42611);
   }

   @Override
   public boolean isShield(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1819 || itemStack.method_31573(ConventionalItemTags.SHIELD_TOOLS);
   }

   @Override
   public boolean isPickaxe(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1810 || itemStack.method_31573(class_3489.field_42614);
   }

   @Override
   public boolean isAxe(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1743 || itemStack.method_31573(class_3489.field_42612);
   }

   @Override
   public boolean isShovel(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1821 || itemStack.method_31573(class_3489.field_42615);
   }

   @Override
   public boolean isHoe(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1794 || itemStack.method_31573(class_3489.field_42613);
   }

   @Override
   public boolean isShears(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1820 || itemStack.method_31573(ConventionalItemTags.SHEAR_TOOLS);
   }

   @Override
   public boolean isFlintAndSteel(class_1799 itemStack) {
      return itemStack.method_7909() instanceof class_1786;
   }
}
