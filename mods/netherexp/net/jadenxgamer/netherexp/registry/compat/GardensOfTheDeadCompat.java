package net.jadenxgamer.netherexp.registry.compat;

import java.awt.Color;
import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.block.SwirlsBlock;
import net.jadenxgamer.netherexp.core.block.WallGrowingWartBlock;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class GardensOfTheDeadCompat {
   public static class Blocks {
      public static final Supplier<Block> SHROOMBLIGHT = RegistryHelper.registerBlock(
         "shroomblight", () -> new Block(Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.SHROOMLIGHT).lightLevel(state -> 10))
      );
      public static final Supplier<Block> BLIGHTWART = RegistryHelper.registerBlock(
         "blightwart",
         () -> new WallGrowingWartBlock(
            Properties.of()
               .mapColor(MapColor.TERRACOTTA_YELLOW)
               .instabreak()
               .noCollission()
               .noOcclusion()
               .randomTicks()
               .pushReaction(PushReaction.DESTROY)
               .sound(SoundType.NETHER_WART)
         )
      );
      public static final Supplier<Block> BLIGHT_SWIRLS = RegistryHelper.registerBlock(
         "blight_swirls",
         () -> new SwirlsBlock(
            new Color(16764272),
            Properties.of()
               .mapColor(MapColor.COLOR_BROWN)
               .replaceable()
               .noCollission()
               .instabreak()
               .lightLevel(state -> state.getValue(SwirlsBlock.ACTIVE) ? 6 : 0)
               .sound(JNESoundType.SOUL_SWIRLS)
         )
      );
      public static final Supplier<Block> YELLOW_MIXED_NETHER_BRICKS = RegistryHelper.registerBlock(
         "yellow_mixed_nether_bricks", () -> new Block(Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.NETHER_BRICKS))
      );
      public static final Supplier<Block> YELLOW_NETHER_BRICKS = RegistryHelper.registerBlock(
         "yellow_nether_bricks",
         () -> new Block(Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.RED_NETHER_BRICKS).mapColor(MapColor.TERRACOTTA_YELLOW))
      );
      public static final Supplier<Block> YELLOW_NETHER_BRICK_SLAB = RegistryHelper.registerBlock(
         "yellow_nether_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)YELLOW_NETHER_BRICKS.get()))
      );
      public static final Supplier<Block> YELLOW_NETHER_BRICK_STAIRS = RegistryHelper.registerBlock(
         "yellow_nether_brick_stairs",
         () -> new StairBlock(YELLOW_NETHER_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)YELLOW_NETHER_BRICKS.get()))
      );
      public static final Supplier<Block> YELLOW_NETHER_BRICK_WALL = RegistryHelper.registerBlock(
         "yellow_nether_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)YELLOW_NETHER_BRICKS.get()))
      );

      public static void init() {
      }
   }

   public static class Items {
      public static final Supplier<Item> BLIGHTSPORES = JNEItems.ITEMS.register("blightspores", () -> new Item(new net.minecraft.world.item.Item.Properties()));

      public static void init() {
      }
   }
}
