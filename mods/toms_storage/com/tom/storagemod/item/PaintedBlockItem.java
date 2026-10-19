package com.tom.storagemod.item;

import com.tom.storagemod.Content;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PaintedBlockItem extends BlockItem {
   public static Function<Block, Item> makeHidden() {
      return b -> new PaintedBlockItem(b, new Properties());
   }

   public static Function<Block, Item> make() {
      return b -> new PaintedBlockItem(b, new Properties());
   }

   private PaintedBlockItem(Block block, Properties p) {
      super(block, p);
   }

   public Component getName(ItemStack is) {
      MutableComponent tc = (MutableComponent)super.getName(is);
      BlockState st = (BlockState)is.get(Content.paintComponent.get());
      if (st != null) {
         tc.append(" (");
         tc.append(st.getBlock().getName().withStyle(ChatFormatting.GREEN));
         tc.append(")");
      }

      return tc;
   }
}
