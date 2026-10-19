package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.api.registrar.Registrar;
import com.blackgear.vanillabackport.common.worldgen.surface.SpatialNoiseThresholdConditionSource;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;

public class ModMaterialConditions {
   public static final Registrar<MapCodec<? extends ConditionSource>> MATERIALS = Registrar.create(Registries.MATERIAL_CONDITION, "minecraft");
   public static final MapCodec<SpatialNoiseThresholdConditionSource> SPATIAL_NOISE_THRESHOLD = (MapCodec<SpatialNoiseThresholdConditionSource>)MATERIALS.register(
      "spatial_noise_threshold", SpatialNoiseThresholdConditionSource.CODEC.codec()
   );
}
