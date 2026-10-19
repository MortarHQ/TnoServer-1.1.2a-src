package com.benji.netherman.init;

import com.benji.netherman.common.effect.ManipulationEffect;
import com.benji.netherman.common.effect.ZoneEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "netherman");
   public static final DeferredHolder<MobEffect, ManipulationEffect> MANIPULATION = EFFECTS.register("manipulation", ManipulationEffect::new);
   public static final DeferredHolder<MobEffect, ZoneEffect> FEAR = EFFECTS.register("fear", () -> new ZoneEffect(0));
   public static final DeferredHolder<MobEffect, ZoneEffect> EXCITEMENT = EFFECTS.register("excitement", () -> new ZoneEffect(16711680));
   public static final DeferredHolder<MobEffect, ZoneEffect> FAITH = EFFECTS.register("faith", () -> new ZoneEffect(8388736));
   public static final DeferredHolder<MobEffect, ZoneEffect> ANXIETY = EFFECTS.register("anxiety", () -> new ZoneEffect(9109504));
   public static final DeferredHolder<MobEffect, ZoneEffect> ALERTNESS = EFFECTS.register("alertness", () -> new ZoneEffect(9109504));
   public static final DeferredHolder<MobEffect, ZoneEffect> DESTINY = EFFECTS.register("destiny", () -> new ZoneEffect(9109504));
   public static final DeferredHolder<MobEffect, ZoneEffect> PRAEMIUM = EFFECTS.register("praemium", () -> new ZoneEffect(9109504));

   public static void init(IEventBus bus) {
      EFFECTS.register(bus);
   }
}
