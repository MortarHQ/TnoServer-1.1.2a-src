package twilightforest.util;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public final class ArrayUtil {
   private ArrayUtil() {
   }

   public static <T> T clamped(T[] array, int index) {
      return array[Mth.clamp(index, 0, array.length - 1)];
   }

   public static <T> T wrapped(T[] array, int index) {
      return array[index % array.length];
   }

   @Nullable
   public static <T> T orNull(T[] array, int index) {
      return index >= 0 && index < array.length ? array[index] : null;
   }

   @Nullable
   public static <T> T randomOrNull(T @Nullable [] array, RandomSource random) {
      return (T)(array != null && array.length != 0 ? Util.getRandom(array, random) : null);
   }

   public static <T> List<T> safeShuffledCopy(T @Nullable [] array, RandomSource random) {
      return array == null ? List.of() : Util.shuffledCopy(array, random);
   }
}
