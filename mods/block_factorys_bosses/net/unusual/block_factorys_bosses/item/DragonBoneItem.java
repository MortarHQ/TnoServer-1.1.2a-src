package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class DragonBoneItem extends Item {
   public DragonBoneItem() {
      super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
   }
}
