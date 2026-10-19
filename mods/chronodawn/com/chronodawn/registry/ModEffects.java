package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.effects.ChronoAegisEffect;
import com.chronodawn.effects.EntropyEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create("chronodawn", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> CHRONO_AEGIS_BUFF = EFFECTS.register("chrono_aegis_buff", ChronoAegisEffect::new);
   public static final RegistrySupplier<MobEffect> ENTROPY = EFFECTS.register("entropy", EntropyEffect::new);

   public static void register() {
      EFFECTS.register();
      ChronoDawn.LOGGER.debug("Registered ModEffects");
   }
}
