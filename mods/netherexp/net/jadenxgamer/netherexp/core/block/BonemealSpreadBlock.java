package net.jadenxgamer.netherexp.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BonemealSpreadBlock extends Block implements BonemealableBlock {
   private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

   public BonemealSpreadBlock(ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature, Properties properties) {
      super(properties);
      this.bonemealFeature = bonemealFeature;
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return level.getBlockState(pos.above()).isAir();
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      level.registryAccess()
         .registry(Registries.CONFIGURED_FEATURE)
         .flatMap(registry -> registry.getHolder(this.bonemealFeature))
         .ifPresent(reference -> ((ConfiguredFeature)reference.value()).place(level, level.getChunkSource().getGenerator(), random, pos));
   }
}
