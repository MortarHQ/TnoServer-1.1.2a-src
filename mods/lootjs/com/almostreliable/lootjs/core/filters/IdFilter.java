package com.almostreliable.lootjs.core.filters;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public interface IdFilter extends Predicate<ResourceLocation> {
   record ByLocation(ResourceLocation location) implements IdFilter {
      public boolean test(ResourceLocation resourceLocation) {
         return this.location.equals(resourceLocation);
      }

      @Override
      public String toString() {
         return "Id[" + this.location + "]";
      }
   }

   record ByMod(String mod) implements IdFilter {
      public boolean test(ResourceLocation resourceLocation) {
         return resourceLocation.getNamespace().equals(this.mod);
      }

      @Override
      public String toString() {
         return "Mod[" + this.mod + "]";
      }
   }

   record ByPattern(Pattern pattern) implements IdFilter {
      public boolean test(ResourceLocation resourceLocation) {
         return this.pattern.matcher(resourceLocation.toString()).matches();
      }

      @Override
      public String toString() {
         return "Pattern[" + this.pattern.pattern() + "]";
      }
   }

   record Or(List<IdFilter> filters) implements IdFilter {
      public boolean test(ResourceLocation resourceLocation) {
         return this.filters.stream().anyMatch(filter -> filter.test(resourceLocation));
      }

      @Override
      public String toString() {
         return "Or[" + StringUtils.join(this.filters, ", ") + "]";
      }
   }
}
