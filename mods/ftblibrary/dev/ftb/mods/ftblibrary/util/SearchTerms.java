package dev.ftb.mods.ftblibrary.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;

public record SearchTerms(List<SearchTerms.Term> terms) {
   public static SearchTerms parse(String str) {
      if (str.isEmpty()) {
         return new SearchTerms(List.of());
      }

      List<SearchTerms.Term> terms = Arrays.stream(str.toLowerCase().split(" +")).map(SearchTerms.Term::of).toList();
      return new SearchTerms(terms);
   }

   public boolean match(ResourceLocation id, String displayName, Predicate<ResourceLocation> tagMatcher) {
      return this.terms.stream().allMatch(term -> {
         if (term.value.isEmpty()) {
            return true;
         }

         return switch (term.type) {
            case SIMPLE -> displayName.toLowerCase().contains(term.value);
            case MOD -> id.getNamespace().contains(term.value);
            case TAG -> tagMatcher.test(ResourceLocation.parse(term.value));
         };
      });
   }

   public record Term(SearchTerms.TermType type, String value) {
      public static SearchTerms.Term of(String s) {
         if (s.isEmpty()) {
            return new SearchTerms.Term(SearchTerms.TermType.SIMPLE, "");
         }

         return switch (s.charAt(0)) {
            case '#' -> new SearchTerms.Term(SearchTerms.TermType.TAG, s.substring(1));
            case '@' -> new SearchTerms.Term(SearchTerms.TermType.MOD, s.substring(1));
            default -> new SearchTerms.Term(SearchTerms.TermType.SIMPLE, s);
         };
      }
   }

   private enum TermType {
      SIMPLE,
      MOD,
      TAG;
   }
}
