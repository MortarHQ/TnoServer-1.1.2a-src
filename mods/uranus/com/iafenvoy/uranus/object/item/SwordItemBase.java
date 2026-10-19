package com.iafenvoy.uranus.object.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

@Deprecated(forRemoval = true)
public class SwordItemBase extends SwordItem implements ISwingable {
   public SwordItemBase(Tier toolMaterial, Properties settings) {
      super(toolMaterial, settings);
   }

   @Override
   public boolean onEntitySwing(ItemStack itemtack, Entity entity) {
      return this.onSwingHand(itemtack, entity.level(), entity.getX(), entity.getY(), entity.getZ());
   }

   @Override
   public boolean onSwingHand(ItemStack itemtack, Level world, double x, double y, double z) {
      return false;
   }
}
