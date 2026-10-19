package net.jadenxgamer.netherexp.registry.compat;

import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.api.registry.ElysiumReflection;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RubinatedNetherCompat {
   public static class Blocks {
      public static final Supplier<Block> SOUL_RUBY_ORE = RegistryHelper.registerBlock(
         "soul_ruby_ore",
         () -> (Block)ElysiumReflection.createBlock()
            .className("corundum.rubinated_nether.content.blocks.MagmaExperienceBlock")
            .constructorValues(
               new Object[]{
                  Properties.ofFullCopy((BlockBehaviour)JNEBlocks.SOUL_MAGMA_BLOCK.get()).strength(2.0F).requiresCorrectToolForDrops(), UniformInt.of(4, 8)
               }
            )
      );

      public static void init() {
      }
   }
}
