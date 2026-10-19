package net.jadenxgamer.elysium_api.impl.util;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public final class BlockStatePropertiesCondition {
   public static final Codec<BlockStatePropertiesCondition> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING)
      .xmap(BlockStatePropertiesCondition::new, BlockStatePropertiesCondition::properties);
   private final Map<String, String> properties;
   private final List<BlockStatePropertiesCondition.PrecompiledRule> rules;
   private final ConcurrentHashMap<Block, List<BlockStatePropertiesCondition.ResolvedRule>> blockCache = new ConcurrentHashMap<>();

   public BlockStatePropertiesCondition(Map<String, String> properties) {
      this.properties = properties;
      this.rules = new ArrayList<>(properties.size());

      for (Entry<String, String> entry : properties.entrySet()) {
         this.rules.add(new BlockStatePropertiesCondition.PrecompiledRule(entry.getKey(), compileCondition(entry.getValue())));
      }
   }

   public Map<String, String> properties() {
      return this.properties;
   }

   public boolean matches(BlockState state) {
      Block block = state.getBlock();
      List<BlockStatePropertiesCondition.ResolvedRule> resolved = this.blockCache.get(block);
      if (resolved == null) {
         resolved = this.resolveForBlock(block);
         this.blockCache.put(block, resolved);
      }

      for (BlockStatePropertiesCondition.ResolvedRule rule : resolved) {
         if (rule.property == null) {
            return false;
         }

         Comparable<?> currentValue = state.getValue(rule.property);
         if (!checkCondition(rule.property, currentValue, rule.condition)) {
            return false;
         }
      }

      return true;
   }

   private List<BlockStatePropertiesCondition.ResolvedRule> resolveForBlock(Block block) {
      List<BlockStatePropertiesCondition.ResolvedRule> resolvedRules = new ArrayList<>(this.rules.size());

      for (BlockStatePropertiesCondition.PrecompiledRule rule : this.rules) {
         Property<?> property = block.getStateDefinition().getProperty(rule.propertyName);
         resolvedRules.add(new BlockStatePropertiesCondition.ResolvedRule(property, rule.condition));
      }

      return resolvedRules;
   }

   private static BlockStatePropertiesCondition.CompiledCondition compileCondition(String conditionValue) {
      String trimmed = conditionValue.trim();
      if (trimmed.isEmpty()) {
         return new BlockStatePropertiesCondition.CompiledCondition(BlockStatePropertiesCondition.Operator.EXACT_MATCH, "");
      }

      char first = trimmed.charAt(0);
      if (first == '!') {
         String target = trimmed.substring(1).trim();
         return target.length() > 1 && target.charAt(0) == '(' && target.charAt(target.length() - 1) == ')'
            ? new BlockStatePropertiesCondition.CompiledCondition(
               BlockStatePropertiesCondition.Operator.NOT, splitOnOr(target.substring(1, target.length() - 1))
            )
            : new BlockStatePropertiesCondition.CompiledCondition(BlockStatePropertiesCondition.Operator.NOT, target);
      }

      if (trimmed.contains("||")) {
         return new BlockStatePropertiesCondition.CompiledCondition(BlockStatePropertiesCondition.Operator.OR, splitOnOr(trimmed));
      }

      if (trimmed.length() >= 2) {
         BlockStatePropertiesCondition.CompiledCondition numeric = tryParseNumeric(trimmed);
         if (numeric != null) {
            return numeric;
         }
      }

      return new BlockStatePropertiesCondition.CompiledCondition(BlockStatePropertiesCondition.Operator.EXACT_MATCH, trimmed);
   }

   private static BlockStatePropertiesCondition.CompiledCondition tryParseNumeric(String s) {
      BlockStatePropertiesCondition.Operator op = null;
      int startIdx = -1;
      if (s.startsWith(">=")) {
         op = BlockStatePropertiesCondition.Operator.GREATER_THAN_OR_EQUAL;
         startIdx = 2;
      } else if (s.startsWith("<=")) {
         op = BlockStatePropertiesCondition.Operator.LESS_THAN_OR_EQUAL;
         startIdx = 2;
      } else if (s.startsWith(">")) {
         op = BlockStatePropertiesCondition.Operator.GREATER_THAN;
         startIdx = 1;
      } else if (s.startsWith("<")) {
         op = BlockStatePropertiesCondition.Operator.LESS_THAN;
         startIdx = 1;
      }

      if (op == null) {
         return null;
      }

      try {
         double value = Double.parseDouble(s.substring(startIdx).trim());
         return new BlockStatePropertiesCondition.CompiledCondition(op, value);
      } catch (NumberFormatException e) {
         ElysiumAPI.LOGGER.warn("Failed to parse numeric condition '{}'", s);
         return null;
      }
   }

   private static String[] splitOnOr(String input) {
      return input.split("\\|\\|", -1);
   }

   private static boolean checkCondition(Property<?> property, Comparable<?> currentValue, BlockStatePropertiesCondition.CompiledCondition condition) {
      return switch (condition.operator) {
         case NOT -> handleNot(property, currentValue, condition);
         case OR -> handleOr(property, currentValue, condition);
         case GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL -> handleNumeric(currentValue, condition);
         default -> compareValues(property, currentValue, condition.values[0]);
      };
   }

   private static boolean handleNot(Property<?> property, Comparable<?> currentValue, BlockStatePropertiesCondition.CompiledCondition condition) {
      String[] values = condition.values;
      if (values.length > 1) {
         for (String value : values) {
            if (compareValues(property, currentValue, value)) {
               return false;
            }
         }

         return true;
      } else {
         return !compareValues(property, currentValue, values[0]);
      }
   }

   private static boolean handleOr(Property<?> property, Comparable<?> currentValue, BlockStatePropertiesCondition.CompiledCondition condition) {
      for (String value : condition.values) {
         if (compareValues(property, currentValue, value)) {
            return true;
         }
      }

      return false;
   }

   private static boolean handleNumeric(Comparable<?> currentValue, BlockStatePropertiesCondition.CompiledCondition condition) {
      if (currentValue instanceof Number currentNum) {
         double current = currentNum.doubleValue();
         double target = condition.numericValue;

         return switch (condition.operator) {
            case GREATER_THAN -> current > target;
            case LESS_THAN -> current < target;
            case GREATER_THAN_OR_EQUAL -> current >= target;
            case LESS_THAN_OR_EQUAL -> current <= target;
            default -> false;
         };
      } else {
         return false;
      }
   }

   private static boolean compareValues(Property<?> property, Comparable<?> currentValue, String conditionValue) {
      Optional<?> parsed = property.getValue(conditionValue);
      return parsed.<Boolean>map(currentValue::equals).orElseGet(() -> currentValue.toString().equals(conditionValue));
   }

   private record CompiledCondition(BlockStatePropertiesCondition.Operator operator, String[] values, double numericValue, boolean isNumeric) {
      CompiledCondition(BlockStatePropertiesCondition.Operator operator, String[] values) {
         this(operator, values, 0.0, false);
      }

      CompiledCondition(BlockStatePropertiesCondition.Operator operator, double numericValue) {
         this(operator, null, numericValue, true);
      }

      CompiledCondition(BlockStatePropertiesCondition.Operator operator, String value) {
         this(operator, new String[]{value}, 0.0, false);
      }
   }

   private enum Operator {
      EXACT_MATCH,
      NOT,
      OR,
      GREATER_THAN,
      LESS_THAN,
      GREATER_THAN_OR_EQUAL,
      LESS_THAN_OR_EQUAL;
   }

   private record PrecompiledRule(String propertyName, BlockStatePropertiesCondition.CompiledCondition condition) {
   }

   private record ResolvedRule(Property<?> property, BlockStatePropertiesCondition.CompiledCondition condition) {
   }
}
