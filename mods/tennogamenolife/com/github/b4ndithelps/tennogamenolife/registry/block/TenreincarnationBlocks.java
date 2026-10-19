package com.github.b4ndithelps.tennogamenolife.registry.block;

import com.github.b4ndithelps.tennogamenolife.block.ChessBlock;
import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class TenreincarnationBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("tennogamenolife");
   public static final DeferredBlock<Block> CHESS_BLOCK = registerBlock("chess_block", ChessBlock::new, new Properties().rarity(Rarity.RARE));

   private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> supplier, Properties properties) {
      DeferredBlock<T> block = BLOCKS.register(name, supplier);
      registerBlockItem(name, block, properties);
      return block;
   }

   private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block, Properties properties) {
      return Tenreincarnationitems.ITEMS.register(name, () -> new BlockItem((Block)block.get(), properties));
   }

   public static void register(IEventBus modEventBus) {
      BLOCKS.register(modEventBus);
   }
}
