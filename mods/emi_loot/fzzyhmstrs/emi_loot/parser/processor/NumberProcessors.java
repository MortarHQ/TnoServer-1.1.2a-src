package fzzyhmstrs.emi_loot.parser.processor;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.mixins.BoundedIntUnaryOperatorAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.ScoreboardValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class NumberProcessors {
   public static MutableComponent processBoolean(Boolean input, String keyTrue, String keyFalse, Object... args) {
      if (input != null) {
         return input ? LText.translatable(keyTrue, args) : LText.translatable(keyFalse, args);
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Boolean null for keys: " + keyTrue + " / " + keyFalse + " in table: " + LootTableParser.currentTable);
      }

      return LText.empty();
   }

   public static MutableComponent processNumberRange(
      MinMaxBounds<?> range, String exact, String between, String atLeast, String atMost, String fallback, Object... args
   ) {
      if (!range.equals(Ints.ANY) && !range.equals(Doubles.ANY)) {
         Optional<? extends Number> min = range.min();
         Optional<? extends Number> max = range.max();
         if (Objects.equals(min, max) && min.isPresent()) {
            List<Object> realArgs = args.length > 0 ? new ArrayList<>(Arrays.stream(args).toList()) : new ArrayList<>();
            realArgs.add(0, min.get());
            return LText.translatable(exact, realArgs.toArray());
         } else if (min.isPresent() && max.isPresent()) {
            List<Object> realArgs = args.length > 0 ? new ArrayList<>(Arrays.stream(args).toList()) : new ArrayList<>();
            realArgs.add(0, max.get());
            realArgs.add(0, min.get());
            return LText.translatable(between, realArgs.toArray());
         } else if (min.isPresent()) {
            List<Object> realArgs = args.length > 0 ? new ArrayList<>(Arrays.stream(args).toList()) : new ArrayList<>();
            realArgs.add(0, min.get());
            return LText.translatable(atLeast, realArgs.toArray());
         } else if (max.isPresent()) {
            List<Object> realArgs = args.length > 0 ? new ArrayList<>(Arrays.stream(args).toList()) : new ArrayList<>();
            realArgs.add(0, max.get());
            return LText.translatable(atMost, realArgs.toArray());
         } else {
            return fallback.isEmpty() ? LText.empty() : LText.translatable(fallback);
         }
      } else {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Non-specific number range for keys: " + exact + " / " + between + " in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      }
   }

   public static MutableComponent processBoundedIntUnaryOperator(IntRange operator) {
      NumberProvider min = ((BoundedIntUnaryOperatorAccessor)operator).getMin();
      NumberProvider max = ((BoundedIntUnaryOperatorAccessor)operator).getMax();
      if (min != null && max != null) {
         if (min.getType() == NumberProviders.CONSTANT && max.getType() == NumberProviders.CONSTANT) {
            float minVal = ((ConstantValue)min).value();
            float maxVal = ((ConstantValue)max).value();
            if (minVal == maxVal) {
               return processLootNumberProvider(min);
            }
         }

         return LText.translatable("emi_loot.operator.between", processLootNumberProvider(min), processLootNumberProvider(max));
      } else {
         if (min != null) {
            return LText.translatable("emi_loot.operator.min", processLootNumberProvider(min));
         }

         if (max != null) {
            return LText.translatable("emi_loot.operator.max", processLootNumberProvider(max));
         }

         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Null or undefined bounded int unary operator in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.operator.unknown");
      }
   }

   public static MutableComponent processLootNumberProvider(NumberProvider provider) {
      LootNumberProviderType type = provider.getType();
      if (type == NumberProviders.CONSTANT) {
         return LText.translatable("emi_loot.number_provider.constant", ((ConstantValue)provider).value());
      }

      if (type == NumberProviders.BINOMIAL) {
         NumberProvider n = ((BinomialDistributionGenerator)provider).n();
         NumberProvider p = ((BinomialDistributionGenerator)provider).p();
         float nVal = getRollAvg(n);
         float pVal = getRollAvg(p);
         MutableComponent nValText = processLootNumberProvider(n);
         MutableComponent pValText = processLootNumberProvider(p);
         float avg = nVal * pVal;
         return LText.translatable("emi_loot.number_provider.binomial", nValText, pValText, avg);
      }

      if (type == NumberProviders.UNIFORM) {
         NumberProvider min = ((UniformGenerator)provider).min();
         NumberProvider max = ((UniformGenerator)provider).max();
         float minVal = getRollAvg(min);
         float maxVal = getRollAvg(max);
         MutableComponent minValText = processLootNumberProvider(min);
         MutableComponent maxValText = processLootNumberProvider(max);
         float avg = (minVal + maxVal) / 2.0F;
         return LText.translatable("emi_loot.number_provider.uniform", minValText, maxValText, avg);
      }

      if (type == NumberProviders.SCORE) {
         String lootScore = ((ScoreboardValue)provider).score();
         float lootScale = ((ScoreboardValue)provider).scale();
         return LText.translatable("emi_loot.number_provider.score", lootScore, lootScale);
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Non-specific or undefined number provider in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.number_provider.unknown");
   }

   public static float getRollAvg(NumberProvider provider) {
      LootNumberProviderType type = provider.getType();
      if (type == NumberProviders.CONSTANT) {
         return ((ConstantValue)provider).value();
      }

      if (type == NumberProviders.BINOMIAL) {
         NumberProvider n = ((BinomialDistributionGenerator)provider).n();
         NumberProvider p = ((BinomialDistributionGenerator)provider).p();
         float nVal = getRollAvg(n);
         float pVal = getRollAvg(p);
         return nVal * pVal;
      }

      if (type == NumberProviders.UNIFORM) {
         NumberProvider min = ((UniformGenerator)provider).min();
         NumberProvider max = ((UniformGenerator)provider).max();
         float minVal = getRollAvg(min);
         float maxVal = getRollAvg(max);
         return (minVal + maxVal) / 2.0F;
      }

      if (type == NumberProviders.SCORE) {
         return 0.0F;
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Loot number provider with unknown type: " + provider.getType().toString());
      }

      return 0.0F;
   }
}
