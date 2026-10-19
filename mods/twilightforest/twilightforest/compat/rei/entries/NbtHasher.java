package twilightforest.compat.rei.entries;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.comparison.EntryComparator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

public class NbtHasher implements EntryComparator<Tag> {
   private final Predicate<String> filter;

   NbtHasher(@Nullable String[] ignoredKeys) {
      if (ignoredKeys != null && ignoredKeys.length != 0) {
         if (ignoredKeys.length == 1) {
            String s = ignoredKeys[0];
            this.filter = key -> !Objects.equals(s, key);
         } else {
            Set<String> set = new HashSet<>(Arrays.asList(ignoredKeys));
            this.filter = Predicates.not(set::contains);
         }
      } else {
         this.filter = key -> true;
      }
   }

   private boolean shouldHash(String key) {
      return this.filter.test(key);
   }

   public long hash(ComparisonContext context, Tag value) {
      return this.hashTag(value);
   }

   private int hashTag(@Nullable Tag tag) {
      if (tag == null) {
         return 0;
      } else if (tag instanceof ListTag list) {
         return this.hashListTag(list);
      } else {
         return tag instanceof CompoundTag compound ? this.hashCompoundTag(compound) : tag.hashCode();
      }
   }

   private int hashListTag(ListTag tag) {
      int i = tag.size();

      for (Tag innerTag : tag) {
         i = i * 31 + this.hashTag(innerTag);
      }

      return i;
   }

   private int hashCompoundTag(CompoundTag tag) {
      int i = 1;

      for (Entry<String, Tag> entry : tag.tags.entrySet()) {
         if (this.shouldHash(entry.getKey())) {
            i = i * 31 + (Objects.hashCode(entry.getKey()) ^ this.hashTag(entry.getValue()));
         }
      }

      return i;
   }
}
