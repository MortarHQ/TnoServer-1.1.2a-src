package dev.ftb.mods.ftbfiltersystem.util;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.IntStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

public class NBTUtil {
   public static boolean compareNbt(@Nullable Tag tagA, @Nullable Tag tagB, boolean fuzzy, boolean compareLists) {
      if (tagA == tagB) {
         return true;
      } else if (tagA == null) {
         return true;
      } else if (tagB == null) {
         return false;
      } else if (!tagA.getType().equals(tagB.getType())) {
         return false;
      } else if (tagA instanceof CompoundTag compoundA && tagB instanceof CompoundTag compoundB) {
         Set<String> keysA = compoundA.getAllKeys();
         Set<String> keysB = compoundB.getAllKeys();
         return fuzzy || keysA.size() == keysB.size() && Sets.intersection(keysA, keysB).size() == keysA.size()
            ? keysA.stream().allMatch(key -> compareNbt(compoundA.get(key), compoundB.get(key), fuzzy, compareLists))
            : false;
      } else if (!(compareLists && tagA instanceof ListTag listA && tagB instanceof ListTag listB)) {
         return tagA.equals(tagB);
      } else if (listA.isEmpty()) {
         return listB.isEmpty();
      } else {
         return listA.size() != listB.size() ? false : IntStream.range(0, listA.size()).allMatch(i -> compareNbt(listA.get(i), listB.get(i), fuzzy, true));
      }
   }
}
