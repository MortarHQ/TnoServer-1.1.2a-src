package net.mcreator.rpgstylemorebosses.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class ExoframePartItem extends Item {
   public ExoframePartItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.RARE));
   }
}
