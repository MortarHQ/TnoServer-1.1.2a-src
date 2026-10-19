package com.mortal_cultivation.registry;

import com.mortal_cultivation.effect.NourishingQiEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegistry {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "mortal_cultivation");
   public static final DeferredHolder<MobEffect, MobEffect> NOURISHING_QI = EFFECTS.register("nourishing", () -> new NourishingQiEffect());

   public static void register(IEventBus modEventBus) {
      EFFECTS.register(modEventBus);
   }
}
