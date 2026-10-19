package com.iafenvoy.uranus.object.item;

import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

@Deprecated(forRemoval = true)
public class ItemBase extends Item implements ISwingable {
   public ItemBase(Function<Properties, Properties> properties) {
      super(properties.apply(new Properties().rarity(Rarity.COMMON).stacksTo(64)));
   }

   public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
      return false;
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
