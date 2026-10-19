package net.blay09.mods.waystones.requirement;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.requirement.ConditionResolver;
import net.blay09.mods.waystones.api.requirement.ParameterSerializer;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.resources.ResourceLocation;

public class RequirementModifierParser {
   public static List<? extends ConfiguredRequirementModifier<?, ?>> parse(String input) {
      try {
         int conditionsStart = input.indexOf(91);
         int conditionsEnd = input.indexOf(93);
         String conditionsPart = conditionsStart != -1 && conditionsEnd != -1 ? input.substring(conditionsStart + 1, conditionsEnd) : "";
         String functionPart = input.substring(conditionsEnd + 1).trim();
         List<ConfiguredCondition<?>> conditions = parseConditions(conditionsPart);
         List<ConfiguredRequirement<?, ?>> requirements = parseRequirements(functionPart);
         return requirements.stream().map(requirement -> new ConfiguredRequirementModifier<>((ConfiguredRequirement<?, ?>)requirement, conditions)).toList();
      } catch (Exception e) {
         Waystones.logger.error("Could not parse warp requirement: {}", input, e);
         return List.of();
      }
   }

   private static List<ConfiguredCondition<?>> parseConditions(String conditionsPart) {
      ArrayList<ConfiguredCondition<?>> conditions = new ArrayList<>();
      Pattern conditionPattern = Pattern.compile("([\\w:]+)(?:\\((.*?)\\))?");
      Matcher conditionMatcher = conditionPattern.matcher(conditionsPart);

      while (conditionMatcher.find()) {
         ResourceLocation conditionId = waystonesResourceLocation(conditionMatcher.group(1));
         String args = conditionMatcher.group(2);
         ConditionResolver<?> conditionResolver = RequirementRegistry.getConditionResolver(conditionId);
         if (conditionResolver == null) {
            throw new IllegalArgumentException("Unknown condition id: " + conditionId);
         }

         conditions.add(parseCondition(conditionResolver, args != null ? args : ""));
      }

      return conditions;
   }

   private static <P> ConfiguredCondition<P> parseCondition(ConditionResolver<P> conditionResolver, String args) {
      P parameters = deserializeParameter(conditionResolver.getParameterType(), args);
      return new ConfiguredCondition<>(conditionResolver, parameters);
   }

   private static List<ConfiguredRequirement<?, ?>> parseRequirements(String functionPart) {
      ArrayList<ConfiguredRequirement<?, ?>> requirements = new ArrayList<>();
      Pattern requirementPattern = Pattern.compile("([\\w:]+)\\((.*?)\\)");
      Matcher requirementMatcher = requirementPattern.matcher(functionPart);

      while (requirementMatcher.find()) {
         requirements.add(parseRequirement(requirementMatcher.group(0)));
      }

      return requirements;
   }

   private static ConfiguredRequirement<?, ?> parseRequirement(String functionPart) {
      Pattern functionPattern = Pattern.compile("([\\w:]+)\\((.*?)\\)");
      Matcher functionMatcher = functionPattern.matcher(functionPart);
      if (functionMatcher.find()) {
         ResourceLocation requirementId = waystonesResourceLocation(functionMatcher.group(1));
         String args = functionMatcher.group(2);
         RequirementFunction<WarpRequirement, Object> requirement = RequirementRegistry.getRequirementFunction(requirementId);
         if (requirement == null) {
            throw new IllegalArgumentException("Unknown requirement id: " + requirementId);
         } else {
            return parseRequirement(requirement, args != null ? args : "");
         }
      } else {
         throw new IllegalArgumentException("Invalid format for requirement modifier: '" + functionPart + "'");
      }
   }

   private static <T extends WarpRequirement, P> ConfiguredRequirement<T, P> parseRequirement(RequirementFunction<T, P> requirement, String args) {
      P parameters = deserializeParameter(requirement.getParameterType(), args);
      return new ConfiguredRequirement<>(requirement, parameters);
   }

   public static ResourceLocation waystonesResourceLocation(String value) {
      int colon = value.indexOf(58);
      String namespace = colon != -1 ? value.substring(0, colon) : "waystones";
      String path = colon != -1 ? value.substring(colon + 1) : value;
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
   }

   public static <T> T deserializeParameter(Class<T> type, String value) {
      ParameterSerializer<T> serializer = RequirementRegistry.getParameterSerializer(type);
      if (serializer == null) {
         throw new IllegalArgumentException("No serializer registered for type " + type);
      } else {
         return serializer.deserialize(value);
      }
   }

   public static <T> T deserializeParameterList(Class<T> type, String commaSeparatedParameters) {
      Constructor<?> constructor = type.getConstructors()[0];
      Class<?>[] parameterTypes = constructor.getParameterTypes();
      String[] parameters = commaSeparatedParameters.split(",");
      if (parameters.length != parameterTypes.length) {
         throw new IllegalArgumentException("Parameter count mismatch for type " + type);
      }

      Object[] parameterValues = new Object[parameters.length];

      for (int i = 0; i < parameters.length; i++) {
         parameterValues[i] = deserializeParameter(parameterTypes[i], parameters[i].trim());
      }

      try {
         return (T)constructor.newInstance(parameterValues);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
