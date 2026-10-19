package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.effect.CrystalInfectionEffect;
import cn.leolezury.eternalstarlight.common.effect.DreamCatcherEffect;
import cn.leolezury.eternalstarlight.common.effect.OblivionEffect;
import cn.leolezury.eternalstarlight.common.effect.StarfireEffect;
import cn.leolezury.eternalstarlight.common.effect.StickyEffect;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ESMobEffects {
   public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, "eternal_starlight");
   public static final RegistryObject<MobEffect, MobEffect> CRYSTAL_INFECTION = MOB_EFFECTS.register(
      "crystal_infection", () -> new CrystalInfectionEffect(MobEffectCategory.HARMFUL, 8001164)
   );
   public static final RegistryObject<MobEffect, MobEffect> DREAM_CATCHER = MOB_EFFECTS.register(
      "dream_catcher", () -> new DreamCatcherEffect(MobEffectCategory.BENEFICIAL, 5343172)
   );
   public static final RegistryObject<MobEffect, MobEffect> STICKY = MOB_EFFECTS.register(
      "sticky", () -> new StickyEffect(MobEffectCategory.BENEFICIAL, 16758447)
   );
   public static final RegistryObject<MobEffect, MobEffect> FLAMMABLE = MOB_EFFECTS.register(
      "flammable", () -> new MobEffect(MobEffectCategory.HARMFUL, 14250876)
   );
   public static final RegistryObject<MobEffect, MobEffect> BRITTLE = MOB_EFFECTS.register("brittle", () -> new MobEffect(MobEffectCategory.HARMFUL, 10220788));
   public static final RegistryObject<MobEffect, MobEffect> NUMBNESS = MOB_EFFECTS.register(
      "numbness", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 5716549)
   );
   public static final RegistryObject<MobEffect, MobEffect> TEARY = MOB_EFFECTS.register("teary", () -> new MobEffect(MobEffectCategory.HARMFUL, 10470339));
   public static final RegistryObject<MobEffect, MobEffect> STARFIRE = MOB_EFFECTS.register(
      "starfire", () -> new StarfireEffect(MobEffectCategory.HARMFUL, 16743741)
   );
   public static final RegistryObject<MobEffect, MobEffect> OBLIVION = MOB_EFFECTS.register(
      "oblivion", () -> new OblivionEffect(MobEffectCategory.BENEFICIAL, 8010636)
   );

   public static void loadClass() {
   }
}
