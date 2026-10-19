package net.blay09.mods.waystones.requirement;

import java.util.HashMap;
import java.util.Map;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.VariableResolver;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.api.requirement.WarpRequirementsContext;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WarpRequirementsContextImpl implements WarpRequirementsContext {
   private final Map<ResourceLocation, WarpRequirement> requirements = new HashMap<>();
   private final WaystoneTeleportContext context;

   public WarpRequirementsContextImpl(WaystoneTeleportContext context) {
      this.context = context;
   }

   public <T extends WarpRequirement, P> void apply(ConfiguredRequirementModifier<T, P> configuredModifier) {
      for (ConfiguredCondition<?> condition : configuredModifier.conditions()) {
         if (!this.matchesCondition(condition)) {
            return;
         }
      }

      ConfiguredRequirement<T, P> requirement = configuredModifier.requirement();
      RequirementFunction<T, P> modifier = requirement.modifier();
      P parameters = requirement.parameters();
      T existing = (T)this.requirements.get(modifier.getRequirementType());
      if (existing == null) {
         existing = RequirementRegistry.<T>getRequirementType(modifier.getRequirementType()).createInstance();
      }

      this.requirements.put(modifier.getRequirementType(), modifier.apply(existing, this, parameters));
   }

   @Override
   public float getContextValue(ResourceLocation id) {
      VariableResolver resolver = RequirementRegistry.getVariableResolver(id);
      if (resolver != null) {
         return resolver.resolve(this.context);
      } else {
         return this.context.getEntity() instanceof Player player ? (float)PlayerWaystoneManager.getCooldownMillisLeft(player, id) / 1000.0F : 0.0F;
      }
   }

   @Override
   public <P> boolean matchesCondition(ConfiguredCondition<P> configuredCondition) {
      return configuredCondition.resolver().matches(this.context, configuredCondition.parameters());
   }

   public WarpRequirement resolve() {
      if (this.requirements.isEmpty()) {
         return NoRequirement.INSTANCE;
      } else {
         return this.requirements.size() == 1 ? this.requirements.values().iterator().next() : new CombinedRequirement(this.requirements.values());
      }
   }
}
