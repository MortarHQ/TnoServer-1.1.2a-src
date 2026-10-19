package com.github.b4ndithelps.tennogamenolife.registry;

import com.github.b4ndithelps.tennogamenolife.effect.WeakenEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
   public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, "tennogamenolife");
   public static final DeferredHolder<MobEffect, WeakenEffect> WEAKEN = MOB_EFFECTS.register("weaken", WeakenEffect::new);
}
