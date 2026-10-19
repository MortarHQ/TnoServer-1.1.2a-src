package com.iafenvoy.uranus.object.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@Deprecated(forRemoval = true)
public class FoilSwordItemBase extends SwordItemBase {
   public FoilSwordItemBase(Tier toolMaterial, Properties settings) {
      super(toolMaterial, settings);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isFoil(ItemStack itemtack) {
      return true;
   }
}
