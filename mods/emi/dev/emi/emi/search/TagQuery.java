package dev.emi.emi.search;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.registry.EmiTags;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;

public class TagQuery extends Query {
   private final Set<Object> valid;

   public TagQuery(String name) {
      String lowerName = name.toLowerCase();
      this.valid = Stream.concat(
            EmiPort.getItemRegistry()
               .getTagNames()
               .filter(
                  t -> EmiTags.hasTranslation((TagKey<?>)t) && EmiTags.getTagName((TagKey<?>)t).getString().toLowerCase().contains(lowerName)
                     ? true
                     : t.location().toString().contains(lowerName)
               )
               .map(t -> EmiPort.getItemRegistry().getTag(t)),
            EmiPort.getBlockRegistry().getTagNames().filter(t -> t.location().toString().contains(lowerName)).map(t -> EmiPort.getBlockRegistry().getTag(t))
         )
         .flatMap(v -> ((Named)v.get()).stream().map(e -> ((ItemLike)e.value()).asItem()))
         .collect(Collectors.toSet());
   }

   @Override
   public boolean matches(EmiStack stack) {
      return this.valid.contains(stack.getKey());
   }
}
