package com.mortal_cultivation.item;

import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public final class NineYinLotusLeafItem extends PlaceOnWaterBlockItem {
   public NineYinLotusLeafItem(Block block, Properties properties) {
      super(block, properties);
   }

   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }
}
