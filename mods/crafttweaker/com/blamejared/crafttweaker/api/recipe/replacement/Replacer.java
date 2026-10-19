package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.replace.ActionBatchReplacement;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/Replacer")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.Replacer")
@ZenRegister
public final class Replacer {
   private final List<IFilteringRule> rules = new ArrayList<>();
   private final List<ReplacementRequest<?>> requests = new ArrayList<>();
   private final AtomicBoolean done = new AtomicBoolean(false);

   private Replacer() {
   }

   @ZenCodeType.Method
   public static Replacer create() {
      return new Replacer();
   }

   @ZenCodeType.Method
   public Replacer filter(IFilteringRule rule) {
      this.checkDone();
      if (!this.rules.contains(rule)) {
         this.rules.add(rule);
      }

      return this;
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, T toReplace, T with) {
      return this.replace(component, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID), toReplace, with);
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, ITargetingStrategy strategy, T toReplace, T with) {
      if (toReplace != with && !component.match(toReplace, with)) {
         DescriptivePredicate<T> predicate = DescriptivePredicate.of(it -> component.match(toReplace, it), toReplace.toString());
         DescriptiveUnaryOperator<T> operator = DescriptiveUnaryOperator.of(it -> with, with.toString());
         return this.replace(component, strategy, predicate, operator);
      } else {
         return this;
      }
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, ITargetingStrategy strategy, T toReplace, Function<T, T> with) {
      DescriptivePredicate<T> predicate = DescriptivePredicate.of(it -> component.match(toReplace, it), toReplace.toString());
      DescriptiveUnaryOperator<T> operator = DescriptiveUnaryOperator.wrap(with::apply);
      return this.replace(component, strategy, predicate, operator);
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, ITargetingStrategy strategy, Predicate<T> toReplace, Function<T, T> with) {
      return this.replace(component, strategy, DescriptivePredicate.wrap(toReplace), DescriptiveUnaryOperator.wrap(with::apply));
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, Predicate<T> toReplace, Function<T, T> with) {
      return this.replace(
         component,
         ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID),
         DescriptivePredicate.wrap(toReplace),
         DescriptiveUnaryOperator.wrap(with::apply)
      );
   }

   @ZenCodeType.Method
   public <T> Replacer replace(IRecipeComponent<T> component, Function<T, T> with) {
      return this.replace(
         component,
         ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID),
         DescriptivePredicate.wrap(Predicates.alwaysTrue()),
         DescriptiveUnaryOperator.wrap(with::apply)
      );
   }

   private <T> Replacer replace(
      IRecipeComponent<T> component, ITargetingStrategy strategy, DescriptivePredicate<T> predicate, DescriptiveUnaryOperator<T> operator
   ) {
      this.checkDone();
      ReplacementRequest<T> request = new ReplacementRequest<>(component, strategy, predicate, operator);
      this.requests.add(request);
      return this;
   }

   @ZenCodeType.Method
   public void execute() {
      this.checkDone();
      this.done.set(true);
      CraftTweakerAPI.apply(ActionBatchReplacement.of(this.rules, this.requests));
   }

   private void checkDone() {
      if (this.done.get()) {
         throw new IllegalArgumentException("Replacer has already been exhausted");
      }
   }
}
