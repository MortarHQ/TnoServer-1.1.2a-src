package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.api.registrar.Registrar;
import com.blackgear.vanillabackport.common.worldgen.surface.NoiseGradientRuleSource;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

public class ModMaterialRules {
   public static final Registrar<MapCodec<? extends RuleSource>> MATERIALS = Registrar.create(Registries.MATERIAL_RULE, "minecraft");
   public static final MapCodec<NoiseGradientRuleSource> NOISE_GRADIENT = (MapCodec<NoiseGradientRuleSource>)MATERIALS.register(
      "noise_gradient", NoiseGradientRuleSource.CODEC.codec()
   );
}
