package com.iafenvoy.uranus.object.item;

import java.util.function.Function;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@Deprecated(forRemoval = true)
public class FoilItemBase extends ItemBase {
   public FoilItemBase(Function<Properties, Properties> properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isFoil(ItemStack itemtack) {
      return true;
   }
}
