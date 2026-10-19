package net.blay09.mods.waystones.requirement;

import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;

public record ConfiguredRequirementModifier<T extends WarpRequirement, P>(ConfiguredRequirement<T, P> requirement, List<ConfiguredCondition<?>> conditions) {
}
