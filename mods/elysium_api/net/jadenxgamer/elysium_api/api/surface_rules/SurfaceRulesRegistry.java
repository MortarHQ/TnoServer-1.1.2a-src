package net.jadenxgamer.elysium_api.api.surface_rules;

import net.jadenxgamer.elysium_api.impl.compat.ElysiumTerrablenderHelper;
import net.jadenxgamer.elysium_api.impl.core.surface_rules.ElysiumSurfaceRulesManager;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.neoforged.fml.ModList;

public class SurfaceRulesRegistry {
   public static void registerOverworldSurfaceRule(RuleSource rule, String namespace) {
      if (ModList.get().isLoaded("terrablender")) {
         ElysiumTerrablenderHelper.addOverworldSurfaceRule(rule, namespace);
      } else {
         ElysiumSurfaceRulesManager.OVERWORLD_SURFACE_RULES.add(rule);
      }
   }

   public static void registerNetherSurfaceRule(RuleSource rule, String namespace) {
      if (ModList.get().isLoaded("terrablender")) {
         ElysiumTerrablenderHelper.addNetherSurfaceRule(rule, namespace);
      } else {
         ElysiumSurfaceRulesManager.NETHER_SURFACE_RULES.add(rule);
      }
   }
}
