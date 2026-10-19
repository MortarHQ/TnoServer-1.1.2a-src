package de.ellpeck.naturesaura.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public record Matcher(BlockState defaultState, Matcher.ICheck check) {
   public static Matcher any() {
      return new Matcher(Blocks.AIR.defaultBlockState(), null);
   }

   public static Matcher tag(Block defaultBlock, TagKey<Block> tag) {
      return new Matcher(defaultBlock.defaultBlockState(), (level, start, offset, pos, state, c) -> state.is(tag));
   }

   public interface ICheck {
      boolean matches(Level var1, BlockPos var2, BlockPos var3, BlockPos var4, BlockState var5, char var6);
   }
}
