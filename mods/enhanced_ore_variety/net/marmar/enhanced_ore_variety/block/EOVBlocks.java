package net.marmar.enhanced_ore_variety.block;

import java.util.function.Supplier;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class EOVBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("enhanced_ore_variety");
   public static final Items BLOCK_ITEMS = DeferredRegister.createItems("enhanced_ore_variety");
   public static final DeferredBlock<Block> DIORITE_COAL_ORE = registerBlockWithItem(
      "diorite_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COAL_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_COPPER_ORE = registerBlockWithItem(
      "diorite_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COPPER_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_IRON_ORE = registerBlockWithItem(
      "diorite_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_GOLD_ORE = registerBlockWithItem(
      "diorite_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_LAPIS_ORE = registerBlockWithItem(
      "diorite_lapis_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.LAPIS_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_REDSTONE_ORE = registerBlockWithItem(
      "diorite_redstone_ore", () -> new RedStoneOreBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.REDSTONE_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_EMERALD_ORE = registerBlockWithItem(
      "diorite_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.EMERALD_ORE))
   );
   public static final DeferredBlock<Block> DIORITE_DIAMOND_ORE = registerBlockWithItem(
      "diorite_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DIAMOND_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_COAL_ORE = registerBlockWithItem(
      "andesite_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COAL_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_COPPER_ORE = registerBlockWithItem(
      "andesite_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COPPER_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_IRON_ORE = registerBlockWithItem(
      "andesite_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_GOLD_ORE = registerBlockWithItem(
      "andesite_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_LAPIS_ORE = registerBlockWithItem(
      "andesite_lapis_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.LAPIS_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_REDSTONE_ORE = registerBlockWithItem(
      "andesite_redstone_ore", () -> new RedStoneOreBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.REDSTONE_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_EMERALD_ORE = registerBlockWithItem(
      "andesite_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.EMERALD_ORE))
   );
   public static final DeferredBlock<Block> ANDESITE_DIAMOND_ORE = registerBlockWithItem(
      "andesite_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DIAMOND_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_COAL_ORE = registerBlockWithItem(
      "granite_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COAL_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_COPPER_ORE = registerBlockWithItem(
      "granite_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COPPER_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_IRON_ORE = registerBlockWithItem(
      "granite_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_GOLD_ORE = registerBlockWithItem(
      "granite_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_LAPIS_ORE = registerBlockWithItem(
      "granite_lapis_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.LAPIS_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_REDSTONE_ORE = registerBlockWithItem(
      "granite_redstone_ore", () -> new RedStoneOreBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.REDSTONE_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_EMERALD_ORE = registerBlockWithItem(
      "granite_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.EMERALD_ORE))
   );
   public static final DeferredBlock<Block> GRANITE_DIAMOND_ORE = registerBlockWithItem(
      "granite_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DIAMOND_ORE))
   );
   public static final DeferredBlock<Block> TUFF_COAL_ORE = registerBlockWithItem(
      "tuff_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_COAL_ORE))
   );
   public static final DeferredBlock<Block> TUFF_COPPER_ORE = registerBlockWithItem(
      "tuff_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_COPPER_ORE))
   );
   public static final DeferredBlock<Block> TUFF_IRON_ORE = registerBlockWithItem(
      "tuff_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE))
   );
   public static final DeferredBlock<Block> TUFF_GOLD_ORE = registerBlockWithItem(
      "tuff_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_GOLD_ORE))
   );
   public static final DeferredBlock<Block> TUFF_LAPIS_ORE = registerBlockWithItem(
      "tuff_lapis_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_LAPIS_ORE))
   );
   public static final DeferredBlock<Block> TUFF_REDSTONE_ORE = registerBlockWithItem(
      "tuff_redstone_ore", () -> new RedStoneOreBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_REDSTONE_ORE))
   );
   public static final DeferredBlock<Block> TUFF_EMERALD_ORE = registerBlockWithItem(
      "tuff_emerald_ore",
      () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_EMERALD_ORE))
   );
   public static final DeferredBlock<Block> TUFF_DIAMOND_ORE = registerBlockWithItem(
      "tuff_diamond_ore",
      () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_DIAMOND_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_COAL_ORE = registerBlockWithItem(
      "blackstone_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COAL_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_COPPER_ORE = registerBlockWithItem(
      "blackstone_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COPPER_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_IRON_ORE = registerBlockWithItem(
      "blackstone_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_GOLD_ORE = registerBlockWithItem(
      "blackstone_gold_ore",
      () -> new DropExperienceBlock(UniformInt.of(0, 1), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_GOLD_ORE).sound(SoundType.STONE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_LAPIS_ORE = registerBlockWithItem(
      "blackstone_lapis_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.LAPIS_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_REDSTONE_ORE = registerBlockWithItem(
      "blackstone_redstone_ore", () -> new RedStoneOreBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.REDSTONE_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_EMERALD_ORE = registerBlockWithItem(
      "blackstone_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.EMERALD_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_DIAMOND_ORE = registerBlockWithItem(
      "blackstone_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DIAMOND_ORE))
   );
   public static final DeferredBlock<Block> BLACKSTONE_QUARTZ_ORE = registerBlockWithItem(
      "blackstone_quartz_ore",
      () -> new DropExperienceBlock(UniformInt.of(2, 5), Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.NETHER_QUARTZ_ORE).sound(SoundType.STONE))
   );

   private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
      DeferredBlock<T> ToReturn = BLOCKS.register(name, block);
      registerBlockItem(name, ToReturn);
      return ToReturn;
   }

   private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
      BLOCK_ITEMS.register(name, () -> new BlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties()));
   }

   public static void register(IEventBus eventBus) {
      BLOCKS.register(eventBus);
      BLOCK_ITEMS.register(eventBus);
   }
}
