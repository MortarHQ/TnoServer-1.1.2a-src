package com.blamejared.crafttweaker.api.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.natives.loot.ExpandLootContext;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.CommonLootModifiers")
@Document("vanilla/api/loot/modifier/CommonLootModifiers")
public final class CommonLootModifiers {
   private static final ILootModifier LOOT_CLEARING_MODIFIER = (loot, context) -> new ArrayList<>();

   @ZenCodeType.Method
   public static ILootModifier add(IItemStack stack) {
      return stack.isEmpty() ? ILootModifier.DEFAULT : (loot, context) -> {
         loot.add(stack.copy());
         return loot;
      };
   }

   @ZenCodeType.Method
   public static ILootModifier addAll(IItemStack... stacks) {
      return chaining(Arrays.stream(stacks).map(CommonLootModifiers::add));
   }

   @ZenCodeType.Method
   public static ILootModifier addWithChance(Percentaged<IItemStack> stack) {
      return isInvalidChance(stack) ? ILootModifier.DEFAULT : (loot, context) -> {
         IItemStack picked = pickStackBasedOnChance(context.getRandom(), stack);
         if (picked != null) {
            loot.add(picked);
         }

         return loot;
      };
   }

   @SafeVarargs
   @ZenCodeType.Method
   public static ILootModifier addAllWithChance(Percentaged<IItemStack>... stacks) {
      return chaining(Arrays.stream(stacks).map(CommonLootModifiers::addWithChance));
   }

   @ZenCodeType.Method
   public static ILootModifier addWithOreDropsBonus(Enchantment enchantment, IItemStack stack) {
      return stack.isEmpty()
         ? ILootModifier.DEFAULT
         : (loot, context) -> {
            IItemStack tool = ExpandLootContext.getTool(context);
            if (tool != null && tool.getInternal() != null && !tool.isEmpty()) {
               int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(
                  Services.REGISTRY.holderOrThrow(Registries.ENCHANTMENT, enchantment), tool.getInternal()
               );
               int oldAmount = stack.amount();
               int newAmount = enchantmentLevel <= 0 ? oldAmount : oldAmount * Math.max(0, context.getRandom().nextInt(enchantmentLevel + 2) - 1) + 1;
               loot.add(stack.copy().withAmount(newAmount));
            } else {
               loot.add(stack.copy());
            }

            return loot;
         };
   }

   @ZenCodeType.Method
   public static ILootModifier addWithBinomialBonus(Enchantment enchantment, int extra, float p, IItemStack stack) {
      return stack.isEmpty()
         ? ILootModifier.DEFAULT
         : (loot, context) -> {
            IItemStack tool = ExpandLootContext.getTool(context);
            if (tool != null && tool.getInternal() != null && !tool.isEmpty()) {
               int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(
                  Services.REGISTRY.holderOrThrow(Registries.ENCHANTMENT, enchantment), tool.getInternal()
               );
               if (enchantmentLevel <= 0) {
                  loot.add(stack.copy());
               } else {
                  RandomSource source = context.getRandom();
                  int oldAmount = stack.amount();
                  int additional = 0;
                  int i = 0;

                  for (int s = enchantmentLevel + extra; i < s; i++) {
                     if (source.nextFloat() < p) {
                        additional++;
                     }
                  }

                  i = oldAmount + additional;
                  loot.add(stack.copy().withAmount(i));
               }
            } else {
               loot.add(stack.copy());
            }

            return loot;
         };
   }

   @ZenCodeType.Method
   public static ILootModifier addWithUniformBonus(Enchantment enchantment, int multiplier, IItemStack stack) {
      return stack.isEmpty()
         ? ILootModifier.DEFAULT
         : (loot, context) -> {
            IItemStack tool = ExpandLootContext.getTool(context);
            if (tool != null && tool.getInternal() != null && !tool.isEmpty()) {
               int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(
                  Services.REGISTRY.holderOrThrow(Registries.ENCHANTMENT, enchantment), tool.getInternal()
               );
               if (enchantmentLevel <= 0) {
                  loot.add(stack.copy());
               } else {
                  int oldAmount = stack.amount();
                  int newAmount = oldAmount + context.getRandom().nextInt(multiplier * enchantmentLevel + 1);
                  loot.add(stack.copy().withAmount(newAmount));
               }
            } else {
               loot.add(stack.copy());
            }

            return loot;
         };
   }

   @ZenCodeType.Method
   public static ILootModifier addAllWithOreDropsBonus(Enchantment enchantment, IItemStack... stacks) {
      return chaining(Arrays.stream(stacks).map(it -> addWithOreDropsBonus(enchantment, it)));
   }

   @ZenCodeType.Method
   public static ILootModifier addAllWithBinomialBonus(Enchantment enchantment, int extra, float p, IItemStack... stacks) {
      return chaining(Arrays.stream(stacks).map(it -> addWithBinomialBonus(enchantment, extra, p, it)));
   }

   @ZenCodeType.Method
   public static ILootModifier addAllWithUniformBonus(Enchantment enchantment, int multiplier, IItemStack... stacks) {
      return chaining(Arrays.stream(stacks).map(it -> addWithUniformBonus(enchantment, multiplier, it)));
   }

   @ZenCodeType.Method
   public static ILootModifier addWithRandomAmount(IItemStack stack, int min, int max) {
      return !stack.isEmpty() && max >= min ? (loot, context) -> {
         int amount = context.getRandom().nextInt(min, max);
         loot.add(stack.copy().withAmount(amount));
         return loot;
      } : ILootModifier.DEFAULT;
   }

   @ZenCodeType.Method
   public static ILootModifier replaceWith(IIngredient target, IItemStack replacement) {
      if (target.isEmpty()) {
         return ILootModifier.DEFAULT;
      } else {
         return replacement.isEmpty() ? remove(target) : (loot, context) -> {
            boolean hasFound = false;
            List<IItemStack> newList = null;

            for (IItemStack stack : loot) {
               if (hasFound) {
                  if (target.matches(stack)) {
                     newList.add(replacement.copy());
                  } else {
                     newList.add(stack);
                  }
               } else if (target.matches(stack)) {
                  hasFound = true;
                  newList = new ArrayList<>();
                  Iterator var8 = loot.iterator();

                  while (true) {
                     if (var8.hasNext()) {
                        IItemStack other = (IItemStack)var8.next();
                        if (other != stack) {
                           newList.add(other);
                           continue;
                        }
                     }

                     newList.add(replacement.copy());
                     break;
                  }
               }
            }

            return newList == null ? loot : newList;
         };
      }
   }

   @ZenCodeType.Method
   public static ILootModifier replaceAllWith(Map<IIngredient, IItemStack> replacementMap) {
      return chaining(replacementMap.entrySet().stream().map(it -> replaceWith(it.getKey(), it.getValue())));
   }

   @ZenCodeType.Method
   public static ILootModifier replaceStackWith(IIngredientWithAmount target, IItemStack replacement) {
      IIngredient ingredient = target.ingredient();
      int amount = target.amount();
      if (ingredient.isEmpty()) {
         return ILootModifier.DEFAULT;
      } else if (amount <= 0) {
         return ILootModifier.DEFAULT;
      } else {
         return replacement.isEmpty() ? remove(target.ingredient()) : (loot, context) -> {
            boolean hasFound = false;
            List<IItemStack> newList = null;

            for (IItemStack stack : loot) {
               if (hasFound) {
                  int stackAmount;
                  if (ingredient.matches(stack) && (stackAmount = stack.amount()) >= amount) {
                     int newAmount = stackAmount / amount;
                     int oldAmount = stackAmount % amount;
                     newList.add(replacement.copy().withAmount(newAmount));
                     if (oldAmount > 0) {
                        newList.add(stack.withAmount(oldAmount));
                     }
                  } else {
                     newList.add(stack);
                  }
               } else {
                  int stackAmount;
                  if (ingredient.matches(stack) && (stackAmount = stack.amount()) >= amount) {
                     hasFound = true;
                     newList = new ArrayList<>();
                     Iterator newAmount = loot.iterator();

                     while (true) {
                        if (newAmount.hasNext()) {
                           IItemStack other = (IItemStack)newAmount.next();
                           if (stack != other) {
                              newList.add(other);
                              continue;
                           }
                        }

                        int newAmountx = stackAmount / amount;
                        int oldAmount = stackAmount % amount;
                        newList.add(replacement.copy().withAmount(newAmountx));
                        if (oldAmount > 0) {
                           newList.add(stack.withAmount(oldAmount));
                        }
                        break;
                     }
                  }
               }
            }

            return newList == null ? loot : newList;
         };
      }
   }

   @ZenCodeType.Method
   public static ILootModifier replaceAllStacksWith(Map<IIngredientWithAmount, IItemStack> replacementMap) {
      return chaining(replacementMap.entrySet().stream().map(it -> replaceStackWith(it.getKey(), it.getValue())));
   }

   @ZenCodeType.Method
   public static ILootModifier remove(IIngredient target) {
      return target.isEmpty() ? ILootModifier.DEFAULT : (loot, context) -> {
         loot.removeIf(target::matches);
         return loot;
      };
   }

   @ZenCodeType.Method
   public static ILootModifier removeExactly(IIngredientWithAmount target) {
      IIngredient ingredient = target.ingredient();
      int amount = target.amount();
      return !ingredient.isEmpty() && amount > 0 ? (loot, context) -> {
         int rollingAmount = amount;
         ListIterator<IItemStack> iterator = loot.listIterator();

         while (iterator.hasNext()) {
            IItemStack stack = iterator.next();
            if (ingredient.matches(stack)) {
               int stackQuantity = stack.amount();
               rollingAmount -= stackQuantity;
               if (rollingAmount >= 0) {
                  iterator.remove();
               } else {
                  stack.asMutable().withAmount(-rollingAmount);
               }

               if (rollingAmount <= 0) {
                  break;
               }
            }
         }

         return loot;
      } : ILootModifier.DEFAULT;
   }

   @ZenCodeType.Method
   public static ILootModifier removeAll(IIngredient... targets) {
      return chaining(Arrays.stream(targets).map(CommonLootModifiers::remove));
   }

   @ZenCodeType.Method
   public static ILootModifier removeExactlyAll(IIngredientWithAmount... targets) {
      return chaining(Arrays.stream(targets).map(CommonLootModifiers::removeExactly));
   }

   @ZenCodeType.Method
   public static ILootModifier clearLoot() {
      return LOOT_CLEARING_MODIFIER;
   }

   @ZenCodeType.Method
   public static ILootModifier chaining(ILootModifier... modifiers) {
      return chaining(Arrays.stream(modifiers));
   }

   @ZenCodeType.Method
   public static ILootModifier clearing(ILootModifier... modifiers) {
      return chaining(Stream.concat(Stream.of(clearLoot()), Arrays.stream(modifiers)));
   }

   private static boolean isInvalidChance(Percentaged<IItemStack> stack) {
      return stack.getData().isEmpty() || stack.getPercentage() <= 0.0;
   }

   private static IItemStack pickStackBasedOnChance(RandomSource random, Percentaged<IItemStack> stack) {
      return random.nextDouble() <= stack.getPercentage() ? stack.getData().copy() : null;
   }

   private static ILootModifier chaining(Stream<ILootModifier> chain) {
      return chain.reduce(ILootModifier.DEFAULT, (first, second) -> {
         if (first == ILootModifier.DEFAULT) {
            return second;
         } else {
            return second == ILootModifier.DEFAULT ? first : (loot, context) -> second.modify(first.modify(loot, context), context);
         }
      });
   }
}
