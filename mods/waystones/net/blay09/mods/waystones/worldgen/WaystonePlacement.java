package net.blay09.mods.waystones.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class WaystonePlacement extends PlacementModifier {
   public static final MapCodec<WaystonePlacement> CODEC = RecordCodecBuilder.mapCodec(
      builder -> builder.group(Types.CODEC.fieldOf("heightmap").forGetter(placement -> placement.heightmap)).apply(builder, WaystonePlacement::new)
   );
   private final Types heightmap;

   public WaystonePlacement() {
      this(Types.OCEAN_FLOOR_WG);
   }

   public WaystonePlacement(Types heightmap) {
      this.heightmap = heightmap;
   }

   public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
      if (!this.isWaystoneChunk(context, pos)) {
         return Stream.empty();
      }

      if (context.getLevel().getLevel().dimension() != Level.NETHER) {
         int x = pos.getX();
         int z = pos.getZ();
         int y = context.getHeight(this.heightmap, x, z);
         return y > context.getMinBuildHeight() ? Stream.of(new BlockPos(x, y, z)) : Stream.of();
      }

      MutableBlockPos mutablePos = pos.mutable();
      int topMostY = context.getHeight(this.heightmap, pos.getX(), pos.getZ());
      mutablePos.setY(topMostY);
      BlockState stateAbove = context.getLevel().getBlockState(mutablePos);

      for (int i = mutablePos.getY(); i >= 1; i--) {
         mutablePos.setY(mutablePos.getY() - 1);
         BlockState state = context.getLevel().getBlockState(mutablePos);
         if (!state.isAir() && state.getFluidState().isEmpty() && stateAbove.isAir() && !state.is(Blocks.BEDROCK)) {
            mutablePos.setY(mutablePos.getY() + 1);
            break;
         }

         stateAbove = state;
      }

      return mutablePos.getY() > 0 ? Stream.of(mutablePos) : Stream.empty();
   }

   public PlacementModifierType<?> type() {
      return (PlacementModifierType<?>)ModWorldGen.waystonePlacement.get();
   }

   private boolean isWaystoneChunk(PlacementContext world, BlockPos pos) {
      int chunkDistance = WaystonesConfig.getActive().worldGen.chunksBetweenWildWaystones;
      if (chunkDistance == 0) {
         return false;
      }

      ResourceLocation dimension = world.getLevel().getLevel().dimension().location();
      Set<ResourceLocation> dimensionAllowList = WaystonesConfig.getActive().worldGen.wildWaystonesDimensionAllowList;
      Set<ResourceLocation> dimensionDenyList = WaystonesConfig.getActive().worldGen.wildWaystonesDimensionDenyList;
      if (!dimensionAllowList.isEmpty() && !dimensionAllowList.contains(dimension)) {
         return false;
      }

      if (!dimensionDenyList.isEmpty() && dimensionDenyList.contains(dimension)) {
         return false;
      }

      int maxDeviation = (int)Math.ceil(chunkDistance / 2.0F);
      int chunkX = pos.getX() / 16;
      int chunkZ = pos.getZ() / 16;
      int devGridX = pos.getX() / 16 * maxDeviation;
      int devGridZ = pos.getZ() / 16 * maxDeviation;
      long seed = world.getLevel().getSeed();
      Random random = new Random(seed * devGridX * devGridZ);
      int chunkOffsetX = random.nextInt(maxDeviation);
      int chunkOffsetZ = random.nextInt(maxDeviation);
      return (chunkX + chunkOffsetX) % chunkDistance == 0 && (chunkZ + chunkOffsetZ) % chunkDistance == 0;
   }
}
