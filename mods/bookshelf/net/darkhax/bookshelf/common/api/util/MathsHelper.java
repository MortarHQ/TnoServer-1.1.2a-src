package net.darkhax.bookshelf.common.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MathsHelper {
   public static final Random RANDOM = new SecureRandom();
   public static final RandomSource RANDOM_SOURCE = RandomSource.create();
   public static final DecimalFormat DECIMAL_2 = new DecimalFormat("##.##");

   public static boolean inRange(double min, double max, double value) {
      return value <= max && value >= min;
   }

   public static double distance(Vec3 first, Vec3 second) {
      double distanceX = first.x - second.x;
      double distanceY = first.y - second.y;
      double distanceZ = first.z - second.z;
      return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
   }

   public static double round(double value, int places) {
      return value >= 0.0 && places > 0 ? BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue() : value;
   }

   public static int nextInt(Random rng, int min, int max) {
      return rng.nextInt(max - min + 1) + min;
   }

   public static int nextInt(RandomSource rng, int min, int max) {
      return rng.nextIntBetweenInclusive(min, max);
   }

   public static boolean percentChance(double chance) {
      return Math.random() < chance;
   }

   public static float average(int... values) {
      return (float)Arrays.stream(values).sum() / values.length;
   }

   public static float percentage(int value, int total) {
      return (float)value / total;
   }

   public static double pixelSize(int pixels) {
      return pixels / 16.0;
   }

   public static AABB boundsForPixels(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      return new AABB(pixelSize(minX), pixelSize(minY), pixelSize(minZ), pixelSize(maxX), pixelSize(maxY), pixelSize(maxZ));
   }

   public static Map<Direction, VoxelShape> createHorizontalShapes(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
      Plane.HORIZONTAL.forEach(dir -> shapes.put(dir, rotateShape(dir, minX, minY, minZ, maxX, maxY, maxZ)));
      return shapes;
   }

   public static VoxelShape rotateShape(Direction facing, double x1, double y1, double z1, double x2, double y2, double z2) {
      return switch (facing) {
         case NORTH -> Block.box(x1, y1, z1, x2, y2, z2);
         case EAST -> Block.box(16.0 - z2, y1, x1, 16.0 - z1, y2, x2);
         case SOUTH -> Block.box(16.0 - x2, y1, 16.0 - z2, 16.0 - x1, y2, 16.0 - z1);
         case WEST -> Block.box(z1, y1, 16.0 - x2, z2, y2, 16.0 - x1);
         default -> throw new IllegalArgumentException("Can not rotate face in direction " + facing.name());
      };
   }

   public static BlockPos randomOffsetHorizontal(BlockPos startPos, RandomSource rng, int range) {
      return randomOffset(startPos, rng, range, 0, range);
   }

   public static BlockPos randomOffset(BlockPos startPos, RandomSource rng, int rangeX, int rangeY, int rangeZ) {
      if (rangeX >= 0 && rangeY >= 0 && rangeZ >= 0) {
         int offsetX = rangeX != 0 ? rng.nextIntBetweenInclusive(-rangeX, rangeX) : 0;
         int offsetY = rangeY != 0 ? rng.nextIntBetweenInclusive(-rangeY, rangeY) : 0;
         int offsetZ = rangeZ != 0 ? rng.nextIntBetweenInclusive(-rangeZ, rangeZ) : 0;
         return startPos.offset(offsetX, offsetY, offsetZ);
      } else {
         throw new IllegalArgumentException("Cannot offset position by '" + rangeX + ", " + rangeY + ", " + rangeZ + "'. Range must be positive!");
      }
   }

   public static int[] encodeBytesToInt(byte[] bytes) {
      int byteCount = bytes.length;
      int msgSize = (byteCount + 3) / 4;
      int encodedLength = 1 + msgSize;
      int[] result = new int[encodedLength];
      result[0] = byteCount;

      for (int i = 0; i < msgSize; i++) {
         int value = 0;

         for (int j = 0; j < 4; j++) {
            int byteIndex = i * 4 + j;
            if (byteIndex < byteCount) {
               value |= (bytes[byteIndex] & 255) << 24 - j * 8;
            }
         }

         result[i + 1] = value;
      }

      return result;
   }

   public static byte[] decodeBytesFromInt(int[] data) {
      if (data.length == 0) {
         return new byte[0];
      }

      int byteCount = data[0];
      byte[] result = new byte[byteCount];

      for (int i = 0; i < data.length - 1; i++) {
         int value = data[i + 1];

         for (int j = 0; j < 4; j++) {
            int byteIndex = i * 4 + j;
            if (byteIndex < byteCount) {
               result[byteIndex] = (byte)(value >> 24 - j * 8 & 0xFF);
            }
         }
      }

      return result;
   }
}
