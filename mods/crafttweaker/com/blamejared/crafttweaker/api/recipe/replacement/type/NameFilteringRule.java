package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/NameFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.NameFilteringRule")
@ZenRegister
public final class NameFilteringRule implements IFilteringRule {
   private final NameFilteringRule.Matcher matcher;

   private NameFilteringRule(NameFilteringRule.Matcher matcher) {
      this.matcher = matcher;
   }

   @ZenCodeType.Method
   public static NameFilteringRule anyOf(String... exactNames) {
      if (exactNames.length < 1) {
         throw new IllegalArgumentException("Unable to build name filtering rule without at least one name");
      } else {
         return of(new NameFilteringRule.ExactMatcher(Set.of(exactNames)));
      }
   }

   @ZenCodeType.Method
   public static NameFilteringRule containing(String contents) {
      if (contents.isEmpty()) {
         throw new IllegalArgumentException("Everything contains the empty string");
      } else {
         return of(new NameFilteringRule.SimpleMatcher(contents));
      }
   }

   @ZenCodeType.Method
   public static NameFilteringRule regex(String regex) {
      return of(new NameFilteringRule.RegexMatcher(Pattern.compile(regex)));
   }

   private static NameFilteringRule of(NameFilteringRule.Matcher matcher) {
      return new NameFilteringRule(matcher);
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(it -> this.matcher.matches(it.id().toString()));
   }

   @Override
   public String describe() {
      return "recipes matching " + this.matcher.toString();
   }

   private record ExactMatcher(Set<String> exact) implements NameFilteringRule.Matcher {
      @Override
      public boolean matches(String s) {
         return this.exact().contains(s);
      }

      @Override
      public String toString() {
         return "exactly one of " + this.exact().toString().replace('[', '{').replace(']', '}');
      }
   }

   @FunctionalInterface
   private interface Matcher {
      boolean matches(String var1);
   }

   private record RegexMatcher(Pattern pattern) implements NameFilteringRule.Matcher {
      @Override
      public boolean matches(String s) {
         return this.pattern().matcher(s).matches();
      }

      @Override
      public String toString() {
         return this.pattern().toString();
      }
   }

   private record SimpleMatcher(String content) implements NameFilteringRule.Matcher {
      @Override
      public boolean matches(String s) {
         return this.content().contains(s);
      }

      @Override
      public String toString() {
         return "anything containing " + this.content();
      }
   }
}
