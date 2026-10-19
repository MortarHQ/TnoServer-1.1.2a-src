package net.blay09.mods.waystones.api.requirement;

public interface WarpRequirementModifierFunction<TRequirement, TParameter> {
   TRequirement apply(TRequirement var1, WarpRequirementsContext var2, TParameter var3);
}
