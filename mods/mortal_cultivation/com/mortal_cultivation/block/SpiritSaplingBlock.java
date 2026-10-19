package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SpiritSaplingBlock extends SaplingBlock {
   public static final MapCodec<SpiritSaplingBlock> CODEC = simpleCodec(SpiritSaplingBlock::new);
   private static final TreeGrower TREE_GROWER = new TreeGrower(
      "mortal_cultivation:spirit_tree", Optional.empty(), Optional.of(WorldgenRegistry.SPIRIT_TREE), Optional.empty()
   );

   public SpiritSaplingBlock(Properties properties) {
      super(TREE_GROWER, properties);
   }

   public MapCodec<? extends SpiritSaplingBlock> codec() {
      return CODEC;
   }

   public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
      if (level.getBiome(pos).is(WorldgenRegistry.SPIRIT_FORESTS)) {
         super.advanceTree(level, pos, state, random);
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return level.getBiome(pos).is(WorldgenRegistry.SPIRIT_FORESTS);
   }
}
