package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.effect.DespairTimeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ExtrasMobEffects {
   public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "tno_tensura_extras");
   public static final DeferredHolder<MobEffect, DespairTimeEffect> DESPAIR_TIME = MOB_EFFECTS.register("despair_time", DespairTimeEffect::new);

   private ExtrasMobEffects() {
   }
}
