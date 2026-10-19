package twilightforest.world.components;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver.CarveSkipChecker;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nullable;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class TFCavesCarver extends WorldCarver<CaveCarverConfiguration> {
   private final boolean isHighlands;
   private final BlockStateProvider wallBlocks;
   private final ImprovedNoise noise;

   public TFCavesCarver(Codec<CaveCarverConfiguration> codec, boolean isHighlands, BlockStateProvider wallBlocks) {
      super(codec);
      this.wallBlocks = wallBlocks;
      this.liquids = ImmutableSet.of(Fluids.WATER, Fluids.LAVA);
      this.isHighlands = isHighlands;
      this.noise = new ImprovedNoise(new LegacyRandomSource(6972119253061020355L));
   }

   public boolean isStartChunk(CaveCarverConfiguration config, RandomSource rand) {
      return this.isHighlands || rand.nextFloat() <= config.probability;
   }

   public boolean carve(
      CarvingContext ctx,
      CaveCarverConfiguration config,
      ChunkAccess access,
      Function<BlockPos, Holder<Biome>> biomePos,
      RandomSource random,
      Aquifer aquifer,
      ChunkPos accessPos,
      CarvingMask mask
   ) {
      if (this.isHighlands && (Mth.clamp(LegacyLandmarkPlacements.manhattanDistanceFromLandmarkCenter(accessPos.x, accessPos.z), 0, 3) & 1) == 1) {
         return false;
      }

      int i = SectionPos.sectionToBlockCoord(this.getRange() * 2 - 1);
      int caveCount = this.isHighlands ? random.nextInt(2) : random.nextInt(this.getCaveBound());

      for (int caveIndex = 0; caveIndex < caveCount; caveIndex++) {
         double x = accessPos.getBlockX(random.nextInt(16));
         double y = config.y.sample(random, ctx);
         double z = accessPos.getBlockZ(random.nextInt(16));
         double horiz = config.horizontalRadiusMultiplier.sample(random);
         double vert = config.verticalRadiusMultiplier.sample(random);
         double floor = config.floorLevel.sample(random);
         CarveSkipChecker checker = (context, dX, dY, dZ, yPos) -> shouldSkip(dX, dY, dZ, floor);
         int tunnelCount = 1;
         if (this.isHighlands || random.nextInt(4) == 0) {
            double horizToVertRatio = config.yScale.sample(random);
            float radius = 1.0F + random.nextFloat() * 6.0F;
            this.createRoom(ctx, config, access, biomePos, aquifer, x, y, z, radius, horizToVertRatio, mask, checker);
            tunnelCount += random.nextInt(4);
         }

         for (int tunnelIndex = 0; tunnelIndex < tunnelCount; tunnelIndex++) {
            float randomRadian = random.nextFloat() * (float) (Math.PI * 2);
            float randomPitch = (random.nextFloat() - 0.5F) / 4.0F;
            float thickness = this.getThickness(random);
            int branchCount = i - random.nextInt(i / 4);
            this.createTunnel(
               ctx,
               config,
               access,
               biomePos,
               random.nextLong(),
               aquifer,
               x,
               y,
               z,
               horiz,
               vert,
               thickness,
               randomRadian,
               randomPitch,
               0,
               branchCount,
               this.getYScale(),
               mask,
               checker
            );
         }
      }

      return true;
   }

   protected boolean carveBlock(
      CarvingContext ctx,
      CaveCarverConfiguration config,
      ChunkAccess access,
      Function<BlockPos, Holder<Biome>> biomePos,
      CarvingMask mask,
      MutableBlockPos posMutable,
      MutableBlockPos posUp,
      Aquifer aquifer,
      MutableBoolean isSurface
   ) {
      BlockPos pos = posMutable.immutable();
      BlockState stateBeforeReplacement = access.getBlockState(pos);
      if (stateBeforeReplacement.is(Blocks.GRASS_BLOCK)
         || stateBeforeReplacement.is(Blocks.MYCELIUM)
         || stateBeforeReplacement.is(Blocks.PODZOL)
         || stateBeforeReplacement.is(Blocks.DIRT_PATH)) {
         isSurface.setTrue();
      }

      if (pos.getY() < access.getMinBuildHeight() + 6) {
         return false;
      }

      if (!this.canReplaceBlock(config, stateBeforeReplacement) && !isDebugEnabled(config)) {
         return false;
      }

      BlockPos chunkOrigin = access.getPos().getWorldPosition();

      for (Direction facing : Direction.values()) {
         BlockPos relative = pos.relative(facing);
         if (isInsideChunk(relative, chunkOrigin) && access.getFluidState(relative).is(FluidTags.WATER)) {
            return false;
         }
      }

      BlockState blockStateToPlace = this.getCarveState(ctx, config, pos, aquifer);
      if (blockStateToPlace != null) {
         RandomSource randomFromPos = ctx.randomState().oreRandom().at(pos);
         if (!access.getFluidState(pos.above(2)).isEmpty()) {
            blockStateToPlace = randomFromPos.nextBoolean() ? Blocks.ROOTED_DIRT.defaultBlockState() : Blocks.COARSE_DIRT.defaultBlockState();
         }

         boolean blockPlaced = access.setBlockState(pos, blockStateToPlace, false) != null;
         if (aquifer.shouldScheduleFluidUpdate() && !blockStateToPlace.getFluidState().isEmpty()) {
            access.markPosForPostprocessing(pos);
         }

         if (isSurface.isTrue()) {
            BlockPos posDown = pos.relative(Direction.DOWN);
            if (access.getBlockState(posDown).is(Blocks.DIRT)) {
               ctx.topMaterial(biomePos, access, posDown, !blockStateToPlace.getFluidState().isEmpty()).ifPresent(state -> {
                  access.setBlockState(posDown, state, false);
                  if (!state.getFluidState().isEmpty()) {
                     access.markPosForPostprocessing(posDown);
                  }
               });
            }
         }

         if (blockPlaced) {
            this.postCarveBlock(access, pos, config, randomFromPos, chunkOrigin);
         }

         return blockPlaced;
      } else {
         return false;
      }
   }

   private static boolean isInsideChunk(BlockPos relative, BlockPos chunkOrigin) {
      int deltaX = relative.getX() - chunkOrigin.getX();
      int deltaZ = relative.getZ() - chunkOrigin.getZ();
      return deltaX >= 0 && deltaZ >= 0 && deltaX <= 15 && deltaZ <= 15;
   }

   private void postCarveBlock(ChunkAccess access, BlockPos pos, CaveCarverConfiguration config, RandomSource rand, BlockPos chunkOrigin) {
      for (Direction facing : Direction.values()) {
         BlockPos directionalRelative = pos.relative(facing);
         if (isInsideChunk(directionalRelative, chunkOrigin)) {
            if (this.isHighlands) {
               if (rand.nextInt(4) == 0 && this.canReplaceBlock(config, access.getBlockState(directionalRelative))) {
                  access.setBlockState(directionalRelative, this.wallBlocks.getState(rand, directionalRelative), false);
               }
            } else if (facing != Direction.DOWN
               && (
                  facing == Direction.UP
                     || access.getBlockState(directionalRelative.above()).isAir()
                     || this.checkNoiseThreshold(directionalRelative, 0.25, 0.5)
               )) {
               BlockState neighboringBlock = access.getBlockState(directionalRelative);
               if (neighboringBlock.is(BlockTags.BASE_STONE_OVERWORLD) || neighboringBlock.getFluidState().is(FluidTags.WATER)) {
                  access.setBlockState(directionalRelative, this.wallBlocks.getState(rand, directionalRelative), false);
               }
            }
         }
      }
   }

   private boolean checkNoiseThreshold(BlockPos pos, double posScalar, double threshold) {
      double noise = this.noise.noise(pos.getX() * posScalar, pos.getY() * posScalar, pos.getZ() * posScalar);
      return noise * 0.5 + 0.5 > threshold;
   }

   protected int getCaveBound() {
      return 4;
   }

   protected float getThickness(RandomSource rand) {
      float f = rand.nextFloat() * 2.0F + rand.nextFloat();
      if (rand.nextInt(10) == 0) {
         f *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
      }

      return f;
   }

   protected double getYScale() {
      return 1.0;
   }

   protected void createRoom(
      CarvingContext ctx,
      CaveCarverConfiguration config,
      ChunkAccess access,
      Function<BlockPos, Holder<Biome>> biomePos,
      Aquifer aquifer,
      double posX,
      double posY,
      double posZ,
      float radius,
      double horizToVertRatio,
      CarvingMask mask,
      CarveSkipChecker checker
   ) {
      double d0 = 1.5 + Mth.sin((float) (Math.PI / 2)) * radius;
      double d1 = d0 * horizToVertRatio;
      this.carveEllipsoid(ctx, config, access, biomePos, aquifer, posX, posY, posZ, d0, d1, mask, checker);
   }

   protected void createTunnel(
      CarvingContext ctx,
      CaveCarverConfiguration config,
      ChunkAccess access,
      Function<BlockPos, Holder<Biome>> biomePos,
      long seed,
      Aquifer aquifer,
      double posX,
      double posY,
      double posZ,
      double horizMult,
      double vertMult,
      float thickness,
      float yaw,
      float pitch,
      int branchIndex,
      int branchCount,
      double horizToVertRatio,
      CarvingMask mask,
      CarveSkipChecker checker
   ) {
      RandomSource random = RandomSource.create(seed);
      int i = random.nextInt(branchCount / 2) + branchCount / 4;
      boolean flag = random.nextInt(6) == 0;
      float f = 0.0F;
      float f1 = 0.0F;

      for (int j = branchIndex; j < branchCount; j++) {
         double horizontalRadius = 1.5 + Mth.sin((float) Math.PI * j / branchCount) * thickness;
         double verticalRadius = horizontalRadius * horizToVertRatio;
         float f2 = Mth.cos(pitch);
         posX += Mth.cos(yaw) * f2;
         float yShift = Mth.sin(pitch);
         posY += !(yShift > 0.0F) && !(posY + yShift > access.getMinBuildHeight() + 10) ? yShift * 0.25F : yShift;
         posZ += Mth.sin(yaw) * f2;
         pitch *= flag ? 0.92F : 0.7F;
         pitch += f1 * 0.1F;
         yaw += f * 0.1F;
         f1 *= 0.9F;
         f *= 0.75F;
         f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (j == i && thickness > 1.0F) {
            this.createTunnel(
               ctx,
               config,
               access,
               biomePos,
               random.nextLong(),
               aquifer,
               posX,
               posY,
               posZ,
               horizMult,
               vertMult,
               random.nextFloat() * 0.5F + 0.5F,
               yaw - (float) (Math.PI / 2),
               pitch / 3.0F,
               j,
               branchCount,
               1.0,
               mask,
               checker
            );
            this.createTunnel(
               ctx,
               config,
               access,
               biomePos,
               random.nextLong(),
               aquifer,
               posX,
               posY,
               posZ,
               horizMult,
               vertMult,
               random.nextFloat() * 0.5F + 0.5F,
               yaw + (float) (Math.PI / 2),
               pitch / 3.0F,
               j,
               branchCount,
               1.0,
               mask,
               checker
            );
            return;
         }

         if (random.nextInt(4) != 0) {
            if (!canReach(access.getPos(), posX, posZ, j, branchCount, thickness)) {
               return;
            }

            boolean shouldEnlargeSphere = posY > access.getMinBuildHeight() + 12 && random.nextInt(48) == 0;
            float sizeMultiplier = shouldEnlargeSphere ? random.nextFloat() * random.nextFloat() * 2.0F + 1.0F : 1.0F;
            double sphereHRadius = Math.min(horizontalRadius * horizMult * sizeMultiplier, 10.0);
            double sphereVRadius = verticalRadius * vertMult * sizeMultiplier;
            double sphereVRadiusLimited = shouldEnlargeSphere ? Math.min(sphereVRadius, sphereHRadius * 0.65F) : sphereVRadius;
            this.carveEllipsoid(ctx, config, access, biomePos, aquifer, posX, posY, posZ, sphereHRadius, sphereVRadiusLimited, mask, checker);
         }
      }
   }

   protected boolean canReplaceBlock(CaveCarverConfiguration config, BlockState state) {
      return !state.is(BlockTags.ICE) && !state.getFluidState().is(FluidTags.WATER) && super.canReplaceBlock(config, state);
   }

   private static boolean shouldSkip(double posX, double posY, double posZ, double minY) {
      return posY <= minY ? true : posX * posX + posY * posY + posZ * posZ >= 1.0;
   }

   @Nullable
   public BlockState getCarveState(CarvingContext context, CaveCarverConfiguration config, BlockPos pos, Aquifer aquifer) {
      return Blocks.CAVE_AIR.defaultBlockState();
   }
}
