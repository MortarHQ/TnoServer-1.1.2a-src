package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.util.CustomTabBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class BlockItemInventoryOnly extends BlockItem implements CustomTabBehavior {
   public BlockItemInventoryOnly(Block p_40565_, Properties p_40566_) {
      super(p_40565_, p_40566_);
   }

   @Override
   public void fillItemCategory(Output contents) {
   }
}
