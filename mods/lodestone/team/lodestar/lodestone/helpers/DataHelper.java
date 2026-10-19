package team.lodestar.lodestone.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DataHelper {
   public static <T, K extends Collection<T>> K reverseOrder(K reversed, Collection<T> items) {
      ArrayList<T> original = new ArrayList<>(items);

      for (int i = items.size() - 1; i >= 0; i--) {
         reversed.add(original.get(i));
      }

      return reversed;
   }

   public static String toTitleCase(String givenString, String regex) {
      String[] stringArray = givenString.split(regex);
      StringBuilder stringBuilder = new StringBuilder();

      for (String string : stringArray) {
         stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
      }

      return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
   }

   public static int[] nextInts(int count, int range) {
      Random rand = new Random();
      int[] ints = new int[count];

      for (int i = 0; i < count; i++) {
         int nextInt;
         do {
            nextInt = rand.nextInt(range);
         } while (!Arrays.stream(ints).noneMatch(j -> j == nextInt));

         ints[i] = nextInt;
      }

      return ints;
   }

   public static <T> boolean hasDuplicate(T[] things) {
      Set<T> thingSet = new HashSet<>();
      return !Arrays.stream(things).allMatch(thingSet::add);
   }

   public static <T> T take(Collection<? extends T> src, T item) {
      src.remove(item);
      return item;
   }

   @SafeVarargs
   public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items) {
      List<T> ret = Arrays.asList(items);

      for (T item : items) {
         if (!src.contains(item)) {
            return Collections.emptyList();
         }
      }

      return !src.removeAll(ret) ? Collections.emptyList() : ret;
   }

   public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred) {
      List<T> ret = new ArrayList<>();
      Iterator<T> iter = src.iterator();

      while (iter.hasNext()) {
         T item = iter.next();
         if (pred.test(item)) {
            iter.remove();
            ret.add(item);
         }
      }

      return ret.isEmpty() ? Collections.emptyList() : ret;
   }

   @SafeVarargs
   public static <T> Collection<T> getAll(Collection<? extends T> src, T... items) {
      return List.copyOf(getAll(src, t -> Arrays.<Object>stream(items).anyMatch(tAgain -> tAgain.getClass().isInstance(t))));
   }

   public static <T> Collection<T> getAll(Collection<T> src, Predicate<T> pred) {
      return src.stream().filter(pred).collect(Collectors.toList());
   }

   public static ArrayList<Vec3> blockOutlinePositions(Level level, BlockPos pos) {
      ArrayList<Vec3> arrayList = new ArrayList<>();
      double d0 = 0.5625;
      RandomSource random = level.random;

      for (Direction direction : Direction.values()) {
         BlockPos blockpos = pos.relative(direction);
         if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
            Axis direction$axis = direction.getAxis();
            double d1 = direction$axis == Axis.X ? 0.5 + d0 * direction.getStepX() : random.nextFloat();
            double d2 = direction$axis == Axis.Y ? 0.5 + d0 * direction.getStepY() : random.nextFloat();
            double d3 = direction$axis == Axis.Z ? 0.5 + d0 * direction.getStepZ() : random.nextFloat();
            arrayList.add(new Vec3(pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3));
         }
      }

      return arrayList;
   }

   public static float distSqr(float... a) {
      float d = 0.0F;

      for (float f : a) {
         d += f * f;
      }

      return d;
   }

   public static float distance(float... a) {
      return Mth.sqrt(distSqr(a));
   }
}
