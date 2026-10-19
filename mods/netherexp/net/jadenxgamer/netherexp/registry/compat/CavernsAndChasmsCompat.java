package net.jadenxgamer.netherexp.registry.compat;

import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.api.registry.ElysiumReflection;
import net.jadenxgamer.netherexp.core.block.LiquidloggedTransparentBlock;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class CavernsAndChasmsCompat {
   public static class Blocks {
      public static final Supplier<Block> NECROMIUM_PLATED_BLOCK = RegistryHelper.registerItemPropertiesBlock(
         "necromium_plated_block",
         () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5F, 0.5F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> CUT_NECROMIUM_BLOCK = RegistryHelper.registerItemPropertiesBlock(
         "cut_necromium_block",
         () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)NECROMIUM_PLATED_BLOCK.get())),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> CUT_NECROMIUM_SLAB = RegistryHelper.registerItemPropertiesBlock(
         "cut_necromium_slab",
         () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)CUT_NECROMIUM_BLOCK.get())),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> CUT_NECROMIUM_STAIRS = RegistryHelper.registerItemPropertiesBlock(
         "cut_necromium_stairs",
         () -> new StairBlock(CUT_NECROMIUM_BLOCK.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)CUT_NECROMIUM_BLOCK.get())),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> CUT_NECROMIUM_PILLAR = RegistryHelper.registerItemPropertiesBlock(
         "cut_necromium_pillar",
         () -> new RotatedPillarBlock(Properties.ofLegacyCopy((BlockBehaviour)CUT_NECROMIUM_BLOCK.get())),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> NECROMIUM_GRATE = RegistryHelper.registerItemPropertiesBlock(
         "necromium_grate",
         () -> new LiquidloggedTransparentBlock(
            Properties.ofLegacyCopy((BlockBehaviour)CUT_NECROMIUM_BLOCK.get())
               .noOcclusion()
               .sound(JNESoundType.NETHERITE_GRATE)
               .lightLevel(LiquidloggedTransparentBlock::stateToProperty)
         ),
         new net.minecraft.world.item.Item.Properties().fireResistant()
      );
      public static final Supplier<Block> TREACHEROUS_BRAZIER = RegistryHelper.registerBlock(
         "treacherous_brazier",
         () -> (Block)ElysiumReflection.createBlock()
            .className("com.teamabnormals.caverns_and_chasms.common.block.BrazierBlock")
            .constructorValues(new Object[]{0.0F, Properties.of().requiresCorrectToolForDrops().strength(3.5F).lightLevel(state -> 9).noOcclusion()})
      );

      public static void init() {
      }
   }

   public static class Items {
      public static final Supplier<Item> NECROMIUM_PLATING = JNEItems.ITEMS
         .register("necromium_plating", () -> new Item(new net.minecraft.world.item.Item.Properties().fireResistant()));

      public static void init() {
      }
   }
}
