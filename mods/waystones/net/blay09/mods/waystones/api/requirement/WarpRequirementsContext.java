package net.blay09.mods.waystones.api.requirement;

import net.blay09.mods.waystones.requirement.ConfiguredCondition;
import net.minecraft.resources.ResourceLocation;

public interface WarpRequirementsContext {
   <P> boolean matchesCondition(ConfiguredCondition<P> var1);

   float getContextValue(ResourceLocation var1);
}
