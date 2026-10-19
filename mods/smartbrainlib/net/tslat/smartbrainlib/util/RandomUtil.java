package net.tslat.smartbrainlib.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RandomUtil {
   public static final RandomUtil.EasyRandom RANDOM = new RandomUtil.EasyRandom(RandomSource.createThreadSafe());

   public static ThreadLocalRandom getRandomInstance() {
      return ThreadLocalRandom.current();
   }

   public static boolean fiftyFifty() {
      return RANDOM.fiftyFifty();
   }

   public static boolean oneInNChance(int n) {
      return RANDOM.oneInNChance(n);
   }

   public static boolean percentChance(double percentChance) {
      return RANDOM.percentChance(percentChance);
   }

   public static boolean percentChance(float percentChance) {
      return RANDOM.percentChance(percentChance);
   }

   public static int randomNumberUpTo(int upperBound) {
      return RANDOM.randomNumberUpTo(upperBound);
   }

   public static float randomValueUpTo(float upperBound) {
      return RANDOM.randomValueUpTo(upperBound);
   }

   public static double randomValueUpTo(double upperBound) {
      return RANDOM.randomValueUpTo(upperBound);
   }

   public static double randomGaussianValue() {
      return RANDOM.randomGaussianValue();
   }

   public static double randomScaledGaussianValue(double scale) {
      return RANDOM.randomScaledGaussianValue(scale);
   }

   public static int randomNumberBetween(int min, int max) {
      return RANDOM.randomNumberBetween(min, max);
   }

   public static double randomValueBetween(double min, double max) {
      return RANDOM.randomValueBetween(min, max);
   }

   @SafeVarargs
   public static <T> T getRandomSelection(@NotNull T... options) {
      return RANDOM.getRandomSelection(options);
   }

   public static <T> T getRandomSelection(@NotNull List<T> options) {
      return RANDOM.getRandomSelection(options);
   }

   @NotNull
   public static BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius) {
      return RANDOM.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius);
   }

   @NotNull
   public static BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world) {
      return RANDOM.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, safeSurfacePlacement, world);
   }

   @NotNull
   public static BlockPos getRandomPositionWithinRange(
      BlockPos centerPos,
      int xRadius,
      int yRadius,
      int zRadius,
      int minSpreadX,
      int minSpreadY,
      int minSpreadZ,
      boolean safeSurfacePlacement,
      Level world,
      int tries,
      @Nullable BiPredicate<BlockState, BlockPos> statePredicate
   ) {
      return RANDOM.getRandomPositionWithinRange(
         centerPos, xRadius, yRadius, zRadius, minSpreadX, minSpreadY, minSpreadZ, safeSurfacePlacement, world, tries, statePredicate
      );
   }

   public static final class EasyRandom implements RandomSource {
      private final RandomSource random;

      public EasyRandom() {
         this(RandomSource.create());
      }

      public EasyRandom(@NotNull RandomSource rand) {
         this.random = rand;
      }

      public RandomSource getSource() {
         return RandomSource.create();
      }

      public boolean fiftyFifty() {
         return this.random.nextBoolean();
      }

      public boolean oneInNChance(int n) {
         return n <= 0 ? false : this.random.nextFloat() < 1.0F / n;
      }

      public boolean percentChance(double percentChance) {
         if (percentChance <= 0.0) {
            return false;
         } else {
            return percentChance >= 1.0 ? true : this.random.nextDouble() < percentChance;
         }
      }

      public boolean percentChance(float percentChance) {
         if (percentChance <= 0.0F) {
            return false;
         } else {
            return percentChance >= 1.0F ? true : this.random.nextDouble() < percentChance;
         }
      }

      public int randomNumberUpTo(int upperBound) {
         return this.random.nextInt(upperBound);
      }

      public float randomValueUpTo(float upperBound) {
         return this.random.nextFloat() * upperBound;
      }

      public double randomValueUpTo(double upperBound) {
         return this.random.nextDouble() * upperBound;
      }

      public double randomGaussianValue() {
         return this.random.nextGaussian();
      }

      public double randomScaledGaussianValue(double scale) {
         return this.random.nextGaussian() * scale;
      }

      public int randomNumberBetween(int min, int max) {
         return min + (int)Math.floor(this.random.nextDouble() * (1 + max - min));
      }

      public double randomValueBetween(double min, double max) {
         return min + this.random.nextDouble() * (max - min);
      }

      public <T> T getRandomSelection(@NotNull T... options) {
         return options[this.random.nextInt(options.length)];
      }

      public <T> T getRandomSelection(@NotNull List<T> options) {
         return options.get(this.random.nextInt(options.size()));
      }

      @NotNull
      public BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius) {
         return this.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, false, null);
      }

      @NotNull
      public BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world) {
         return this.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, 0, 0, 0, safeSurfacePlacement, world, 1, null);
      }

      @NotNull
      public BlockPos getRandomPositionWithinRange(
         BlockPos centerPos,
         int xRadius,
         int yRadius,
         int zRadius,
         int minSpreadX,
         int minSpreadY,
         int minSpreadZ,
         boolean safeSurfacePlacement,
         Level world,
         int tries,
         @Nullable BiPredicate<BlockState, BlockPos> statePredicate
      ) {
         MutableBlockPos mutablePos = centerPos.mutable();
         xRadius = Math.max(xRadius - minSpreadX, 0);
         yRadius = Math.max(yRadius - minSpreadY, 0);
         zRadius = Math.max(zRadius - minSpreadZ, 0);

         for (int i = 0; i < tries; i++) {
            double xAdjust = this.random.nextFloat() * xRadius * 2.0F - xRadius;
            double yAdjust = this.random.nextFloat() * yRadius * 2.0F - yRadius;
            double zAdjust = this.random.nextFloat() * zRadius * 2.0F - zRadius;
            int newX = (int)Math.floor(centerPos.getX() + xAdjust + minSpreadX * Math.signum(xAdjust));
            int newY = (int)Math.floor(centerPos.getY() + yAdjust + minSpreadY * Math.signum(yAdjust));
            int newZ = (int)Math.floor(centerPos.getZ() + zAdjust + minSpreadZ * Math.signum(zAdjust));
            mutablePos.set(newX, newY, newZ);
            if (safeSurfacePlacement && world != null) {
               mutablePos.set(world.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, mutablePos));
            }

            if (statePredicate == null || statePredicate.test(world.getBlockState(mutablePos), mutablePos.immutable())) {
               return mutablePos.immutable();
            }
         }

         return centerPos;
      }

      public RandomUtil.EasyRandom fork() {
         return new RandomUtil.EasyRandom(this.random.fork());
      }

      public PositionalRandomFactory forkPositional() {
         return this.random.forkPositional();
      }

      public void setSeed(long seed) {
         this.random.setSeed(seed);
      }

      public int nextInt() {
         return this.random.nextInt();
      }

      public int nextInt(int upperLimit) {
         return this.random.nextInt(upperLimit);
      }

      public long nextLong() {
         return this.random.nextLong();
      }

      public boolean nextBoolean() {
         return this.random.nextBoolean();
      }

      public float nextFloat() {
         return this.random.nextFloat();
      }

      public double nextDouble() {
         return this.random.nextDouble();
      }

      public double nextGaussian() {
         return this.random.nextGaussian();
      }
   }
}
