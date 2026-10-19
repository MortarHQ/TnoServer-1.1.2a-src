package twilightforest.world.components.structures.fallentrunk;

import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.Beardifier.Rigid;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import twilightforest.world.components.chunkgenerators.TanhHillFunction;

public class TrunkUnderDensityFunction extends Beardifier {
   private final boolean isBigTree;
   private final boolean isXOriented;
   private final RandomSource random;
   private final BoundingBox boundingBox;
   private final List<TanhHillFunction> tanhHillFunctions;
   protected final BoundingBox moundBase;
   protected static final float MOUND_RADIUS = 3.0F;
   protected static final int MAX_RANDOM_RADIUS_INCREASE_BIG_TREE = 4;
   protected static final int MAX_RANDOM_RADIUS_INCREASE_NON_BIG_TREE = 2;
   protected static final float BIG_TREE_MOUND_HEIGHT = 3.0F;
   protected static final float NON_BIG_TREE_MOUND_HEIGHT = 1.5F;

   public TrunkUnderDensityFunction(ObjectListIterator<Rigid> pieceIterator, FallenTrunkPiece piece, boolean isBigTree, int minMounds, int maxMounds) {
      super(pieceIterator, (ObjectListIterator)ObjectIterators.emptyIterator());
      this.isBigTree = isBigTree;
      this.boundingBox = this.getFallenTrunkPiece().box();
      this.random = RandomSource.create(this.boundingBox.minX() * 14413411L + this.boundingBox.minZ() * 43387781L);
      this.isXOriented = this.boundingBox.maxX() - this.boundingBox.minX() > this.boundingBox.maxZ() - this.boundingBox.minZ();
      int length = this.isXOriented ? this.boundingBox.getXSpan() : this.boundingBox.getZSpan();
      Vec3i moundBaseCorner = new Vec3i(this.isXOriented ? length / 2 : 0, 1, !this.isXOriented ? length / 2 : 0);
      this.moundBase = BoundingBox.fromCorners(moundBaseCorner, moundBaseCorner);
      this.tanhHillFunctions = new ArrayList<>();
      int attemptedMoundsNumber = this.random.nextInt(minMounds, maxMounds + 1);

      for (int i = 0; i < attemptedMoundsNumber; i++) {
         Optional<TanhHillFunction> function = this.getTanhHillFunctionWithoutCoveringHole(piece, i % 2 == 0);
         function.ifPresent(this.tanhHillFunctions::add);
      }
   }

   public double compute(FunctionContext context) {
      int x = context.blockX();
      int y = context.blockY();
      int z = context.blockZ();
      int groundLevelDelta = this.getFallenTrunkPiece().groundLevelDelta();
      int horizontalDistanceX = Math.max(0, Math.max(this.boundingBox.minX() - x, x - this.boundingBox.maxX()));
      int horizontalDistanceZ = Math.max(0, Math.max(this.boundingBox.minZ() - z, z - this.boundingBox.maxZ()));
      int adjustedGroundLevel = this.boundingBox.minY() + groundLevelDelta + (this.isBigTree ? 1 : 0);
      int verticalDistance = y - adjustedGroundLevel;
      double moundContribution = this.computeMoundsContribution(context);
      return moundContribution > 0.0 ? moundContribution : flatSurroundingTerrain(horizontalDistanceX, verticalDistance, horizontalDistanceZ);
   }

   public static double flatSurroundingTerrain(double x, double y, double z) {
      if (y > 20.0 || y < -10.0) {
         return 0.0;
      }

      if (x == 0.0 && z == 0.0 && y > 0.0) {
         return -1.0;
      }

      double a = 10.0;
      double c = 10.0;
      double verticalScale = 0.7;
      double normX = Math.abs(x / 10.0);
      double normZ = Math.abs(z / 10.0);
      double normalizedSquaredDistance = normX * normX + normZ * normZ;
      if (normalizedSquaredDistance >= 1.0) {
         return 0.0;
      }

      double normalizedDistance = Math.sqrt(normalizedSquaredDistance);
      double factor = 1.0 - normalizedDistance;
      if (y < 0.0) {
         y = normalizedDistance * 0.5 + y / 5.0;
      }

      return -y * factor / 0.7 * Math.exp(-normalizedDistance / Math.max(1.0 - normalizedDistance, 1.0E-8));
   }

   protected double computeMoundsContribution(FunctionContext context) {
      int x = context.blockX() - this.boundingBox.minX();
      int y = context.blockY() - this.boundingBox.minY();
      int z = context.blockZ() - this.boundingBox.minZ();
      int radius = getRadius(this.boundingBox);
      double ax = Math.abs((this.isXOriented ? z : x) - radius + 1);
      double az = Math.abs(y - radius + 1);
      if (radius == 2.0) {
         if (Math.abs((this.isXOriented ? z : x) - 1.5) + Math.abs(y - 1.5) <= 2.0) {
            return -1.0;
         }
      } else if ((int)(Math.max(ax, az) + Math.min(ax, az) * 0.5) < radius) {
         return -1.0;
      }

      double max = -1.0;

      for (TanhHillFunction h : this.tanhHillFunctions) {
         double value = h.compute(context);
         if (value > max) {
            max = value;
         }
      }

      return max;
   }

   protected Optional<TanhHillFunction> getTanhHillFunctionWithoutCoveringHole(FallenTrunkPiece piece, boolean isOnRightSide) {
      if (this.isBigTree) {
         return Optional.ofNullable(
            this.getTanhHillFunction((BlockPos)Util.getRandom(piece.getAllPotentialBaseMoundBlockPos(isOnRightSide).toArray(), this.random), isOnRightSide)
         );
      }

      Set<BlockPos> blockPosSet = piece.getAllowedBaseMoundBlockPos(isOnRightSide);
      if (blockPosSet.isEmpty()) {
         return Optional.empty();
      }

      BlockPos moundBLockPos = (BlockPos)Util.getRandom(blockPosSet.toArray(), this.random);
      return Optional.ofNullable(this.getTanhHillFunction(moundBLockPos, isOnRightSide));
   }

   protected TanhHillFunction getTanhHillFunction(BlockPos baseBlockPos, boolean isOnRightSide) {
      int maxRandomRadiusIncrease = this.isBigTree ? 4 : 2;
      float moundRadius = 3.0F + this.random.nextInt(0, maxRandomRadiusIncrease);
      float moundHeight = this.isBigTree ? 3.0F : 1.5F;
      float moundBiasAngle = (float) (Math.PI * 2) * this.random.nextFloat();
      return new TanhHillFunction(
         baseBlockPos.getX(), baseBlockPos.getY(), baseBlockPos.getZ(), moundRadius, moundHeight, moundBiasAngle, this.isXOriented, isOnRightSide
      );
   }

   protected Rigid getFallenTrunkPiece() {
      Rigid piece = (Rigid)this.pieceIterator.next();
      this.pieceIterator.back(Integer.MAX_VALUE);
      return piece;
   }

   private static int getRadius(BoundingBox box) {
      return getRadius(box.getYSpan());
   }

   private static int getRadius(int diameter) {
      return (int)Math.ceil(diameter / 2.0);
   }
}
