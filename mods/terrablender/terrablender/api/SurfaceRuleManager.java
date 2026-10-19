package terrablender.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import terrablender.worldgen.TBSurfaceRuleData;
import terrablender.worldgen.surface.NamespacedSurfaceRuleSource;

public class SurfaceRuleManager {
   private static Map<SurfaceRuleManager.RuleCategory, Map<String, RuleSource>> surfaceRules = Maps.newHashMap();
   private static Map<SurfaceRuleManager.RuleCategory, RuleSource> defaultSurfaceRules = Maps.newHashMap();
   private static Map<SurfaceRuleManager.RuleCategory, Map<SurfaceRuleManager.RuleStage, List<Pair<Integer, RuleSource>>>> defaultSurfaceRuleInjections = Maps.newHashMap();

   public static void addSurfaceRules(SurfaceRuleManager.RuleCategory category, String namespace, RuleSource rules) {
      surfaceRules.get(category).put(namespace, rules);
   }

   public static void addToDefaultSurfaceRulesAtStage(
      SurfaceRuleManager.RuleCategory category, SurfaceRuleManager.RuleStage ruleStage, int priority, RuleSource rules
   ) {
      defaultSurfaceRuleInjections.get(category).get(ruleStage).add(Pair.of(priority, rules));
   }

   public static void setDefaultSurfaceRules(SurfaceRuleManager.RuleCategory category, RuleSource rules) {
      defaultSurfaceRules.put(category, rules);
   }

   public static void removeSurfaceRules(SurfaceRuleManager.RuleCategory category, String namespace) {
      surfaceRules.get(category).remove(namespace);
   }

   public static RuleSource getNamespacedRules(SurfaceRuleManager.RuleCategory category, RuleSource fallback) {
      Builder<String, RuleSource> builder = ImmutableMap.builder();
      builder.put("minecraft", getDefaultSurfaceRules(category));
      builder.putAll(surfaceRules.get(category));
      return new NamespacedSurfaceRuleSource(fallback, builder.build());
   }

   public static List<RuleSource> getDefaultSurfaceRuleAdditionsForStage(SurfaceRuleManager.RuleCategory category, SurfaceRuleManager.RuleStage ruleStage) {
      return defaultSurfaceRuleInjections.get(category)
         .get(ruleStage)
         .stream()
         .sorted(Comparator.comparing(Pair::getFirst, Comparator.reverseOrder()))
         .map(Pair::getSecond)
         .collect(ImmutableList.toImmutableList());
   }

   public static RuleSource getDefaultSurfaceRules(SurfaceRuleManager.RuleCategory category) {
      if (defaultSurfaceRules.containsKey(category)) {
         return defaultSurfaceRules.get(category);
      } else if (category == SurfaceRuleManager.RuleCategory.NETHER) {
         return TBSurfaceRuleData.nether();
      } else {
         return category == SurfaceRuleManager.RuleCategory.END ? TBSurfaceRuleData.end() : TBSurfaceRuleData.overworld();
      }
   }

   static {
      for (SurfaceRuleManager.RuleCategory category : SurfaceRuleManager.RuleCategory.values()) {
         surfaceRules.put(category, Maps.newHashMap());
         Map<SurfaceRuleManager.RuleStage, List<Pair<Integer, RuleSource>>> ruleStages = Maps.newHashMap();

         for (SurfaceRuleManager.RuleStage stage : SurfaceRuleManager.RuleStage.values()) {
            ruleStages.put(stage, Lists.newArrayList());
         }

         defaultSurfaceRuleInjections.put(category, ruleStages);
      }
   }

   public enum RuleCategory {
      OVERWORLD,
      NETHER,
      END;
   }

   public enum RuleStage {
      BEFORE_BEDROCK,
      AFTER_BEDROCK;
   }
}
