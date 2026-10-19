package dev.emi.emi.search;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.registry.EmiTags;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.tags.TagKey;

public class RegexTagQuery extends Query {
   private final Set<Object> valid;

   public RegexTagQuery(String name) {
      Pattern p = null;

      try {
         p = Pattern.compile(name, 2);
      } catch (Exception var4) {
      }

      if (p == null) {
         this.valid = Set.of();
      } else {
         Pattern pat = p;
         this.valid = Stream.concat(
               EmiPort.getItemRegistry()
                  .getTagNames()
                  .filter(
                     t -> EmiTags.hasTranslation((TagKey<?>)t) && pat.matcher(EmiTags.getTagName((TagKey<?>)t).getString().toLowerCase()).find()
                        ? true
                        : pat.matcher(t.location().toString()).find()
                  )
                  .map(t -> EmiPort.getItemRegistry().getTag(t)),
               EmiPort.getBlockRegistry().getTagNames().filter(t -> pat.matcher(t.location().toString()).find()).map(t -> EmiPort.getBlockRegistry().getTag(t))
            )
            .flatMap(v -> v.stream())
            .collect(Collectors.toSet());
      }
   }

   @Override
   public boolean matches(EmiStack stack) {
      return this.valid.contains(stack.getKey());
   }
}
