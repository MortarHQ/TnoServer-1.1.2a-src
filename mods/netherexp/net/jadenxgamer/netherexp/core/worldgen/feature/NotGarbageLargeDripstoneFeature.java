package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Column.Range;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class NotGarbageLargeDripstoneFeature extends Feature<NotGarbageLargeDripstoneFeature.Config> {
   public NotGarbageLargeDripstoneFeature(Codec<NotGarbageLargeDripstoneFeature.Config> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NotGarbageLargeDripstoneFeature.Config> context) {
      WorldGenLevel worldGenLevel = context.level();
      BlockPos blockPos = context.origin();
      NotGarbageLargeDripstoneFeature.Config configuration = (NotGarbageLargeDripstoneFeature.Config)context.config();
      RandomSource randomSource = context.random();
      if (!isEmptyOrWater(worldGenLevel, blockPos)) {
         return false;
      }

      Optional<Column> optional = Column.scan(
         worldGenLevel, blockPos, configuration.floorToCeilingSearchRange(), DripstoneUtils::isEmptyOrWater, state -> isBaseOrLava(state, configuration)
      );
      if (!optional.isEmpty() && optional.get() instanceof Range) {
         Range range = (Range)optional.get();
         if (range.height() < 4) {
            return false;
         }

         int i = (int)(range.height() * configuration.maxColumnRadiusToCaveHeightRatio());
         int j = Mth.clamp(i, configuration.columnRadius().getMinValue(), configuration.columnRadius().getMaxValue());
         int k = Mth.randomBetweenInclusive(randomSource, configuration.columnRadius().getMinValue(), j);
         NotGarbageLargeDripstoneFeature.LargeDripstone largeDripstone = makeDripstone(
            blockPos.atY(range.ceiling() - 1), false, randomSource, k, configuration.stalactiteBluntness(), configuration.heightScale()
         );
         NotGarbageLargeDripstoneFeature.LargeDripstone largeDripstone2 = makeDripstone(
            blockPos.atY(range.floor() + 1), true, randomSource, k, configuration.stalagmiteBluntness(), configuration.heightScale()
         );
         NotGarbageLargeDripstoneFeature.WindOffsetter windOffsetter;
         if (largeDripstone.isSuitableForWind(configuration) && largeDripstone2.isSuitableForWind(configuration)) {
            windOffsetter = new NotGarbageLargeDripstoneFeature.WindOffsetter(blockPos.getY(), randomSource, configuration.windSpeed());
         } else {
            windOffsetter = NotGarbageLargeDripstoneFeature.WindOffsetter.noWind();
         }

         boolean bl = largeDripstone.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
         boolean bl2 = largeDripstone2.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
         if (bl) {
            largeDripstone.placeBlocks(worldGenLevel, randomSource, windOffsetter, configuration);
         }

         if (bl2) {
            largeDripstone2.placeBlocks(worldGenLevel, randomSource, windOffsetter, configuration);
         }

         return true;
      } else {
         return false;
      }
   }

   private static NotGarbageLargeDripstoneFeature.LargeDripstone makeDripstone(
      BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase
   ) {
      return new NotGarbageLargeDripstoneFeature.LargeDripstone(root, pointingUp, radius, bluntnessBase.sample(random), scaleBase.sample(random));
   }

   private void placeDebugMarkers(WorldGenLevel level, BlockPos pos, Range range, NotGarbageLargeDripstoneFeature.WindOffsetter windOffsetter) {
      level.setBlock(windOffsetter.offset(pos.atY(range.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
      level.setBlock(windOffsetter.offset(pos.atY(range.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

      for (MutableBlockPos mutableBlockPos = pos.atY(range.floor() + 2).mutable();
         mutableBlockPos.getY() < range.ceiling() - 1;
         mutableBlockPos.move(Direction.UP)
      ) {
         BlockPos blockPos = windOffsetter.offset(mutableBlockPos);
         if (isEmptyOrWater(level, blockPos) || level.getBlockState(blockPos).is(Blocks.DRIPSTONE_BLOCK)) {
            level.setBlock(blockPos, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
         }
      }
   }

   protected static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
      return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava);
   }

   protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int radius) {
      if (isEmptyOrWaterOrLava(level, pos)) {
         return false;
      }

      float f = 6.0F;
      float g = 6.0F / radius;

      for (float h = 0.0F; h < (float) (Math.PI * 2); h += g) {
         int i = (int)(Mth.cos(h) * radius);
         int j = (int)(Mth.sin(h) * radius);
         if (isEmptyOrWaterOrLava(level, pos.offset(i, 0, j))) {
            return false;
         }
      }

      return true;
   }

   protected static double getDripstoneHeight(double radius, double maxRadius, double scale, double minRadius) {
      if (radius < minRadius) {
         radius = minRadius;
      }

      double d = 0.384;
      double e = radius / maxRadius * 0.384;
      double f = 0.75 * Math.pow(e, 1.3333333333333333);
      double g = Math.pow(e, 0.6666666666666666);
      double h = 0.3333333333333333 * Math.log(e);
      double i = scale * (f - g - h);
      i = Math.max(i, 0.0);
      return i / 0.384 * maxRadius;
   }

   protected static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
      return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
   }

   public static boolean isBaseOrLava(BlockState state, NotGarbageLargeDripstoneFeature.Config config) {
      return isBase(state, config) || state.is(Blocks.LAVA);
   }

   public static boolean isBase(BlockState state, NotGarbageLargeDripstoneFeature.Config config) {
      return config.baseBlocks().contains(state.getBlockHolder());
   }

   public record Config(
      int floorToCeilingSearchRange,
      BlockState block,
      HolderSet<Block> baseBlocks,
      IntProvider columnRadius,
      FloatProvider heightScale,
      float maxColumnRadiusToCaveHeightRatio,
      FloatProvider stalactiteBluntness,
      FloatProvider stalagmiteBluntness,
      FloatProvider windSpeed,
      int minRadiusForWind,
      float minBluntnessForWind
   ) implements FeatureConfiguration {
      public static final Codec<NotGarbageLargeDripstoneFeature.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.intRange(1, 512)
                  .fieldOf("floor_to_ceiling_search_range")
                  .orElse(30)
                  .forGetter(NotGarbageLargeDripstoneFeature.Config::floorToCeilingSearchRange),
               BlockState.CODEC.fieldOf("block").orElse(Blocks.DRIPSTONE_BLOCK.defaultBlockState()).forGetter(NotGarbageLargeDripstoneFeature.Config::block),
               RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("base_blocks").forGetter(NotGarbageLargeDripstoneFeature.Config::baseBlocks),
               IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(NotGarbageLargeDripstoneFeature.Config::columnRadius),
               FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(NotGarbageLargeDripstoneFeature.Config::heightScale),
               Codec.floatRange(0.1F, 1.0F)
                  .fieldOf("max_column_radius_to_cave_height_ratio")
                  .forGetter(NotGarbageLargeDripstoneFeature.Config::maxColumnRadiusToCaveHeightRatio),
               FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(NotGarbageLargeDripstoneFeature.Config::stalactiteBluntness),
               FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(NotGarbageLargeDripstoneFeature.Config::stalagmiteBluntness),
               FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(NotGarbageLargeDripstoneFeature.Config::windSpeed),
               Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(NotGarbageLargeDripstoneFeature.Config::minRadiusForWind),
               Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(NotGarbageLargeDripstoneFeature.Config::minBluntnessForWind)
            )
            .apply(instance, NotGarbageLargeDripstoneFeature.Config::new)
      );
   }

   static final class LargeDripstone {
      private BlockPos root;
      private final boolean pointingUp;
      private int radius;
      private final double bluntness;
      private final double scale;

      LargeDripstone(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
         this.root = root;
         this.pointingUp = pointingUp;
         this.radius = radius;
         this.bluntness = bluntness;
         this.scale = scale;
      }

      private int getHeight() {
         return this.getHeightAtRadius(0.0F);
      }

      private int getMinY() {
         return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
      }

      private int getMaxY() {
         return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
      }

      boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, NotGarbageLargeDripstoneFeature.WindOffsetter windOffsetter) {
         while (this.radius > 1) {
            MutableBlockPos mPos = this.root.mutable();
            int i = Math.min(10, this.getHeight());

            for (int j = 0; j < i; j++) {
               if (level.getBlockState(mPos).is(Blocks.LAVA)) {
                  return false;
               }

               if (NotGarbageLargeDripstoneFeature.isCircleMostlyEmbeddedInStone(level, windOffsetter.offset(mPos), this.radius)) {
                  this.root = mPos;
                  return true;
               }

               mPos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
            }

            this.radius /= 2;
         }

         return false;
      }

      private int getHeightAtRadius(float radius) {
         return (int)NotGarbageLargeDripstoneFeature.getDripstoneHeight(radius, this.radius, this.scale, this.bluntness);
      }

      void placeBlocks(
         WorldGenLevel level,
         RandomSource random,
         NotGarbageLargeDripstoneFeature.WindOffsetter windOffsetter,
         NotGarbageLargeDripstoneFeature.Config largeDripstoneConfiguration
      ) {
         for (int i = -this.radius; i <= this.radius; i++) {
            for (int j = -this.radius; j <= this.radius; j++) {
               float f = Mth.sqrt(i * i + j * j);
               if (!(f > this.radius)) {
                  int k = this.getHeightAtRadius(f);
                  if (k > 0) {
                     if (random.nextFloat() < 0.2) {
                        k = (int)(k * Mth.randomBetween(random, 0.8F, 1.0F));
                     }

                     MutableBlockPos mutableBlockPos = this.root.offset(i, 0, j).mutable();
                     boolean bl = false;
                     int l = this.pointingUp ? level.getHeight(Types.WORLD_SURFACE_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) : Integer.MAX_VALUE;

                     for (int m = 0; m < k && mutableBlockPos.getY() < l; m++) {
                        BlockPos blockPos = windOffsetter.offset(mutableBlockPos);
                        if (NotGarbageLargeDripstoneFeature.isEmptyOrWaterOrLava(level, blockPos)) {
                           bl = true;
                           level.setBlock(blockPos, largeDripstoneConfiguration.block(), 2);
                        } else if (bl && level.getBlockState(blockPos).is(BlockTags.BASE_STONE_NETHER)) {
                           break;
                        }

                        mutableBlockPos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                     }
                  }
               }
            }
         }
      }

      boolean isSuitableForWind(NotGarbageLargeDripstoneFeature.Config config) {
         return this.radius >= config.minRadiusForWind() && this.bluntness >= config.minBluntnessForWind();
      }
   }

   private static final class WindOffsetter {
      private final int originY;
      @Nullable
      private final Vec3 windSpeed;

      WindOffsetter(int originY, RandomSource random, FloatProvider magnitude) {
         this.originY = originY;
         float f = magnitude.sample(random);
         float g = Mth.randomBetween(random, 0.0F, (float) Math.PI);
         this.windSpeed = new Vec3(Mth.cos(g) * f, 0.0, Mth.sin(g) * f);
      }

      private WindOffsetter() {
         this.originY = 0;
         this.windSpeed = null;
      }

      static NotGarbageLargeDripstoneFeature.WindOffsetter noWind() {
         return new NotGarbageLargeDripstoneFeature.WindOffsetter();
      }

      BlockPos offset(BlockPos pos) {
         if (this.windSpeed == null) {
            return pos;
         }

         int i = this.originY - pos.getY();
         Vec3 vec3 = this.windSpeed.scale(i);
         return pos.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
      }
   }
}
