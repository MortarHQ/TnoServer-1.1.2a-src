package io.github.manasmods.tensura_neb.item;

import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.TwoHandedSwordItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class WhiteTigerAzureDragonHalberdItem extends TwoHandedSwordItem {
   public WhiteTigerAzureDragonHalberdItem() {
      super(
         TensuraToolTiers.HIHIIROKANE,
         18,
         -3.0F,
         2.0,
         0.75,
         50.0,
         0.0,
         12,
         -3.2F,
         2.0,
         0.5,
         30.0,
         0.0,
         new Properties().arch$tab(TensuraCreativeTabs.GEARS).fireResistant().rarity(Rarity.EPIC).durability(6000)
      );
   }

   public boolean isFoil(ItemStack itemStack) {
      return false;
   }
}
