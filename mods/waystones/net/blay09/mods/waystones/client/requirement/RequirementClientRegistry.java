package net.blay09.mods.waystones.client.requirement;

import java.util.HashMap;
import java.util.Map;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.requirement.CombinedRequirement;
import net.blay09.mods.waystones.requirement.CooldownRequirement;
import net.blay09.mods.waystones.requirement.ExperienceLevelRequirement;
import net.blay09.mods.waystones.requirement.ExperiencePointsRequirement;
import net.blay09.mods.waystones.requirement.ItemRequirement;
import net.blay09.mods.waystones.requirement.RefuseRequirement;
import net.blay09.mods.waystones.requirement.SoftCooldownRequirement;

public class RequirementClientRegistry {
   private static final Map<Class<? extends WarpRequirement>, RequirementRenderer<?>> renderers = new HashMap<>();

   public static <T extends WarpRequirement> void registerRenderer(Class<T> displayClass, RequirementRenderer<T> renderer) {
      renderers.put(displayClass, renderer);
   }

   public static <T extends WarpRequirement> RequirementRenderer<T> getRenderer(Class<T> displayClass) {
      return (RequirementRenderer<T>)renderers.get(displayClass);
   }

   public static <T extends WarpRequirement> RequirementRenderer<T> getRenderer(T requirement) {
      return (RequirementRenderer<T>)renderers.get(requirement.getClass());
   }

   public static void registerDefaults() {
      registerRenderer(CooldownRequirement.class, new CooldownRequirementRenderer());
      registerRenderer(SoftCooldownRequirement.class, new SoftCooldownRequirementRenderer());
      registerRenderer(ExperienceLevelRequirement.class, new ExperienceLevelRequirementRenderer());
      registerRenderer(ExperiencePointsRequirement.class, new ExperiencePointsRequirementRenderer());
      registerRenderer(ItemRequirement.class, new ItemRequirementRenderer());
      registerRenderer(RefuseRequirement.class, new RefuseRequirementRenderer());
      registerRenderer(CombinedRequirement.class, new CombinedRequirementRenderer());
   }
}
