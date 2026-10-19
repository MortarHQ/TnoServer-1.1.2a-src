package io.github.manasmods.tensura_neb.item;

import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.TensuraSwordItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class NightRoseItem extends TensuraSwordItem {
   public NightRoseItem() {
      super(
         TensuraToolTiers.HIHIIROKANE,
         13,
         -2.3F,
         1.0,
         0.0,
         0.0,
         0.0,
         new Properties().arch$tab(TensuraCreativeTabs.GEARS).fireResistant().rarity(Rarity.EPIC).durability(9000)
      );
   }

   public boolean isFoil(ItemStack itemStack) {
      return false;
   }
}
