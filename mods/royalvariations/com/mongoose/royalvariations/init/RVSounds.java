package com.mongoose.royalvariations.init;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVSounds {
   public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, "royalvariations");
   public static final DeferredHolder<SoundEvent, SoundEvent> ROYAL_ENDERMAN_CAST = create("royal_enderman_cast");
   public static final DeferredHolder<SoundEvent, SoundEvent> ROYAL_BOMB = create("royal_bomb");
   public static final DeferredHolder<SoundEvent, SoundEvent> END_SPLASH = create("end_splash");
   public static final DeferredHolder<SoundEvent, SoundEvent> TIMER_TICKING = create("timer_ticking");

   public static void init(IEventBus modEventBus) {
      SOUNDS.register(modEventBus);
   }

   static DeferredHolder<SoundEvent, SoundEvent> create(String name) {
      SoundEvent event = SoundEvent.createVariableRangeEvent(RoyalVariations.location(name));
      return SOUNDS.register(name, () -> event);
   }
}
