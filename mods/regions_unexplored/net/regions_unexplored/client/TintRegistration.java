package net.regions_unexplored.client;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.regions_unexplored.client.color.RuColors;

public class TintRegistration {
   public static Map<BlockColor, Block[]> BLOCK_TINTS = new HashMap<>();
   public static Map<ItemColor, ItemLike[]> ITEM_TINTS = new HashMap<>();

   public static void registerBlockColorHandlers(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block event) {
      RuColors.tintBlocks();
      BLOCK_TINTS.forEach(event::register);
   }

   public static void registerItemColorHandlers(Item event) {
      RuColors.tintItems();
      ITEM_TINTS.forEach(event::register);
   }
}
