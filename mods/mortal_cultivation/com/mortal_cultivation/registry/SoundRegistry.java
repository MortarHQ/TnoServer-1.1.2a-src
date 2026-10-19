package com.mortal_cultivation.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SoundRegistry {
   private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, "mortal_cultivation");
   public static final DeferredHolder<SoundEvent, SoundEvent> SPIRIT_FOREST_MUSIC = SOUND_EVENTS.register(
      "music.spirit_forest", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "music.spirit_forest"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> SPIRIT_MONKEY_AMBIENT = SOUND_EVENTS.register(
      "entity.spirit_monkey.ambient",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "entity.spirit_monkey.ambient"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> SPIRIT_MONKEY_HURT = SOUND_EVENTS.register(
      "entity.spirit_monkey.hurt",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "entity.spirit_monkey.hurt"))
   );

   private SoundRegistry() {
   }

   public static void register(IEventBus bus) {
      SOUND_EVENTS.register(bus);
   }
}
