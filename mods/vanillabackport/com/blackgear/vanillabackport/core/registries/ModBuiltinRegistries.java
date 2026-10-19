package com.blackgear.vanillabackport.core.registries;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.common.api.wolf.WolfSoundVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.CatDataVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.CowVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.FrogDataVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.PigVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.WolfDataVariant;
import com.blackgear.vanillabackport.common.level.entities.sulfurcube.SulfurCubeArchetype;

public class ModBuiltinRegistries {
   public static final BuiltInCoreRegistry<WolfSoundVariant> WOLF_SOUND_VARIANTS = new BuiltInCoreRegistry(ModRegistries.WOLF_SOUND_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<CowVariant> COW_VARIANTS = new BuiltInCoreRegistry(ModRegistries.COW_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<ChickenVariant> CHICKEN_VARIANTS = new BuiltInCoreRegistry(ModRegistries.CHICKEN_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<PigVariant> PIG_VARIANTS = new BuiltInCoreRegistry(ModRegistries.PIG_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<FrogDataVariant> FROG_VARIANTS = new BuiltInCoreRegistry(ModRegistries.FROG_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<WolfDataVariant> WOLF_VARIANTS = new BuiltInCoreRegistry(ModRegistries.WOLF_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<CatDataVariant> CAT_VARIANTS = new BuiltInCoreRegistry(ModRegistries.CAT_VARIANT, "minecraft");
   public static final BuiltInCoreRegistry<SulfurCubeArchetype> SULFUR_CUBE_ARCHETYPES = new BuiltInCoreRegistry(
      ModRegistries.SULFUR_CUBE_ARCHETYPE, "minecraft"
   );
}
