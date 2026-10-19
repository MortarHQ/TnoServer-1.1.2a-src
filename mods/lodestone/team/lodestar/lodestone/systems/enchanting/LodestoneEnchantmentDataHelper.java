package team.lodestar.lodestone.systems.enchanting;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentHelper.EnchantmentVisitor;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.item.enchantment.effects.AllOf.EntityEffects;
import net.minecraft.world.item.enchantment.effects.AllOf.LocationBasedEffects;
import net.minecraft.world.item.enchantment.effects.AllOf.ValueEffects;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.CommonHooks;

public class LodestoneEnchantmentDataHelper {
   public static void runIterationOnItem(ItemStack stack, @Nullable Holder<Enchantment> filter, EnchantmentVisitor visitor) {
      runIterationOnItem(stack, filter, visitor, () -> false);
   }

   public static void runIterationOnItem(ItemStack stack, @Nullable Holder<Enchantment> filter, EnchantmentVisitor visitor, BooleanSupplier breakCondition) {
      ItemEnchantments itemenchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
      RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
      if (lookup != null) {
         itemenchantments = stack.getAllEnchantments(lookup);
      }

      for (Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
         Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
         if (filter == null || filter.is(enchantment.getKey())) {
            visitor.accept(enchantment, entry.getIntValue());
            if (breakCondition.getAsBoolean()) {
               return;
            }
         }
      }
   }

   protected static <T> List<T> getMatchingEffects(
      ItemStack item, DataComponentMap map, Class<T> type, Either<Predicate<TypedDataComponent<?>>, Predicate<T>> condition
   ) {
      ArrayList<Object> effectObjects = new ArrayList<>();
      ArrayList<T> result = new ArrayList<>();

      for (TypedDataComponent<?> component : map) {
         Object value = component.value();
         if (condition.left().map(e -> e.test(component)).orElse(true)) {
            if (value instanceof Collection<?> effectList) {
               effectObjects.addAll((Collection<? extends Object>)effectList);
            } else {
               effectObjects.add(value);
            }
         }
      }

      for (Object object : effectObjects) {
         for (T effect : getEffects(item, object, type)) {
            if (condition.right().map(e -> e.test(effect)).orElse(true)) {
               result.add(effect);
            }
         }
      }

      return result;
   }

   public static <T> List<T> getEffects(ItemStack item, Object object, Class<T> type) {
      ArrayList<T> effects = new ArrayList<>();
      ArrayList<ConditionalEffect<?>> conditionalEffects = new ArrayList<>();
      ArrayList<TargetedConditionalEffect<?>> targetedConditionalEffects = new ArrayList<>();
      if (object instanceof EntityEffects all) {
         for (EnchantmentEntityEffect effect : all.effects()) {
            effects.addAll(getEffects(item, effect, type));
         }
      } else if (object instanceof LocationBasedEffects all) {
         for (EnchantmentLocationBasedEffect effect : all.effects()) {
            effects.addAll(getEffects(item, effect, type));
         }
      } else if (object instanceof ValueEffects all) {
         for (EnchantmentValueEffect effect : all.effects()) {
            effects.addAll(getEffects(item, effect, type));
         }
      }

      if (type.isInstance(object)) {
         effects.add(type.cast(object));
      } else if (object instanceof TargetedConditionalEffect<?> conditionalEffect) {
         targetedConditionalEffects.add(conditionalEffect);
      } else if (object instanceof ConditionalEffect<?> conditionalEffect) {
         conditionalEffects.add(conditionalEffect);
      }

      for (ConditionalEffect<?> conditionalEffect : conditionalEffects) {
         Optional<LootItemCondition> requirements = conditionalEffect.requirements();
         if (requirements.isEmpty() || matches(item, requirements.get())) {
            effects.addAll(getEffects(item, conditionalEffect.effect(), type));
         }
      }

      for (TargetedConditionalEffect<?> conditionalEffect : targetedConditionalEffects) {
         Optional<LootItemCondition> requirements = conditionalEffect.requirements();
         if (requirements.isEmpty() || matches(item, requirements.get())) {
            effects.addAll(getEffects(item, conditionalEffect.effect(), type));
         }
      }

      return effects;
   }

   public static boolean matches(ItemStack item, LootItemCondition condition) {
      if (condition instanceof AllOfCondition all) {
         return all.terms.stream().allMatch(t -> innerMatches(item, t));
      } else {
         return condition instanceof AnyOfCondition any ? any.terms.stream().anyMatch(t -> innerMatches(item, t)) : innerMatches(item, condition);
      }
   }

   protected static boolean innerMatches(ItemStack item, LootItemCondition condition) {
      if (condition instanceof InvertedLootItemCondition var7) {
         InvertedLootItemCondition var11 = var7;

         try {
            var12 = var11.term();
         } catch (Throwable var5) {
            throw new MatchException(var5.toString(), var5);
         }

         LootItemCondition var9 = var12;
         LootItemCondition term = var9;
         return !innerMatches(item, term);
      } else if (condition instanceof MatchTool var2) {
         MatchTool var10000 = var2;

         try {
            var10 = var10000.predicate();
         } catch (Throwable var6) {
            throw new MatchException(var6.toString(), var6);
         }

         Optional var4 = var10;
         Optional<ItemPredicate> predicate = var4;
         return predicate.isPresent() && predicate.get().test(item);
      } else {
         return true;
      }
   }
}
