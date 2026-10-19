package net.jadenxgamer.elysium_api.impl.compat;

import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.SurfaceRuleManager.RuleCategory;

public class ElysiumTerrablenderHelper {
   public static void addOverworldSurfaceRule(RuleSource rules, String namespace) {
      SurfaceRuleManager.addSurfaceRules(RuleCategory.OVERWORLD, namespace, rules);
   }

   public static void addNetherSurfaceRule(RuleSource rules, String namespace) {
      SurfaceRuleManager.addSurfaceRules(RuleCategory.NETHER, namespace, rules);
   }
}
